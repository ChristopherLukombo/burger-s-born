package fr.esgi.service.impl;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Refund;
import com.paypal.api.payments.RefundRequest;
import com.paypal.api.payments.Sale;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import fr.esgi.config.ConfigurationService;
import fr.esgi.config.Constants;
import fr.esgi.config.ErrorMessage;
import fr.esgi.domain.Command;
import fr.esgi.domain.Menu;
import fr.esgi.domain.Product;
import fr.esgi.service.CommandService;
import fr.esgi.service.PayPalService;
import fr.esgi.service.dto.CommandDTO;
import fr.esgi.service.dto.Paypal;
import fr.esgi.service.mapper.CommandMapper;

/**
 * Service Implementation for managing PayPal.
 */
@Service("PayPalService")
public class PayPalServiceImpl implements PayPalService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PayPalServiceImpl.class);

	private final CommandService commandService;

	private final CommandMapper commandMapper;

	private final ConfigurationService configurationService;

	@Autowired
	public PayPalServiceImpl(CommandService commandService, CommandMapper commandMapper,
			ConfigurationService configurationService) {
		this.commandService = commandService;
		this.commandMapper = commandMapper;
		this.configurationService = configurationService;
	}

	/**
	 *  Create a payment.
	 *
	 * @param commandDTO command to create
	 * @return status
	 */
	@Override
	public Map<String, Object> createPayment(CommandDTO commandDTO) {
		Map<String, Object> response = new HashMap<>();
		Payer payer = getPayer();
		Payment payment = getPayment(Arrays.asList(createTransaction(commandDTO)), payer);
		payment.setRedirectUrls(getRedirectUrls());
		try {
			APIContext context = getApiContext();
			Payment createdPayment = payment.create(context);
			if (null != createdPayment) {
				response.put(Constants.STATUS, Constants.SUCCESS);
				response.put(Constants.REDIRECT_URL, getRedirectUrl(createdPayment));
				commandDTO.setOrderStatus(Constants.WAITTING);
				commandDTO.setPaymentId(createdPayment.getId());
				Command command = toCommand(commandDTO);
				commandDTO.setPrice(BigDecimal.valueOf((getPrice(command.getProducts(), command.getMenus()))));
				CommandDTO result = commandService.save(commandDTO);
				if (null != result) {
					response.put(Constants.WAITTING, getRedirectUrl(createdPayment));
				}
			}
		} catch (PayPalRESTException e) {
			LOGGER.error(ErrorMessage.ERROR_HAPPENED_DURING_PAYMENT_CREATION, e);
		}
		return response;
	}

	private Transaction createTransaction(CommandDTO commandDTO) {
		ItemList itemList = new ItemList();
		Command command = toCommand(commandDTO);
		List<Item> items = getItems(command);
		itemList.setItems(items);
		Double price = getPrice(command.getProducts(), command.getMenus());
		Details details = getDetails((price != null) ? price : 0);
		Amount amount = getAmount(details, price);
		return getTransaction(itemList, amount);
	}

	private Command toCommand(CommandDTO commandDTO) {
		return commandMapper.commandDTOToCommand(commandDTO);
	}

	private String getRedirectUrl(Payment createdPayment) {
		String redirectUrl = "";
		List<Links> links = createdPayment.getLinks();
		for (Links link : links) {
			if(Constants.APPROVAL_URL.equals(link.getRel())){
				redirectUrl = link.getHref();
				break;
			}
		}
		return redirectUrl;
	}

	private RedirectUrls getRedirectUrls() {
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(configurationService.getPaypalCancelUrl());
		redirectUrls.setReturnUrl(configurationService.getPaypalReturnUrl());
		return redirectUrls;
	}

	private Payer getPayer() {
		Payer payer = new Payer();
		payer.setPaymentMethod(Constants.PAYPAL);
		return payer;
	}

	private Payment getPayment(List<Transaction> transactions, Payer payer) {
		Payment payment = new Payment();
		payment.setTransactions(transactions);
		payment.setIntent(Constants.SALE);
		payment.setPayer(payer);
		return payment;
	}

	private Details getDetails(Double price) {
		Details details = new Details();
		details.setSubtotal(String.valueOf(price));
		return details;
	}

	private Transaction getTransaction(ItemList itemList, Amount amount) {
		Transaction transaction = new Transaction();
		transaction.setItemList(itemList);
		transaction.setDescription(Constants.ACHAT_SUR_BURGER_S_TERMINAL);
		transaction.setAmount(amount);
		return transaction;
	}

	private Amount getAmount(Details details, final Double price) {
		Amount amount = new Amount();
		amount.setTotal(String.valueOf(price));
		amount.setCurrency(Constants.EUR);
		amount.setDetails(details);
		return amount;
	}

	private List<Item> getItems(Command command) {
		List<Item> items = new ArrayList<>();
		for (Menu menu : command.getMenus()) {
			for (Product product : menu.getProducts()) {
				Item item = new Item();
				item.setName(product.getName());
				item.setPrice(String.valueOf(product.getPrice()));
				item.setCurrency(Constants.EUR);
				item.setQuantity(String.valueOf(1));
				items.add(item);
			}
		}
		getOptionalProducts(command, items);

		return items;
	}

	private void getOptionalProducts(Command command, List<Item> items) {
		List<Product> optionalproducts = command.getProducts();
		if (!optionalproducts.isEmpty()) {
			for (Product product : optionalproducts) {
				Item item = new Item();
				item.setName(product.getName());
				item.setPrice(String.valueOf(product.getPrice()));
				item.setCurrency(Constants.EUR);
				item.setQuantity(String.valueOf(1));
				items.add(item);
			}
		}
	}

	private double getPrice(List<Product> products, List<Menu> menus) {
		Optional<Double> priceProducts = calculatePriceProducts(products);
		double sumPrice = 0;
		if (priceProducts.isPresent()) {
			sumPrice += priceProducts.get().doubleValue();
		}
		Optional<Double> priceMenus = calculatePriceMenus(menus);
		if (priceMenus.isPresent()) {
			sumPrice += priceMenus.get().doubleValue();
		}
		return sumPrice;
	}

	private static Optional<Double> calculatePriceProducts(List<Product> products) {
		return products.stream()
				.map(Product::getPrice)
				.reduce(Double::sum);
	}

	private static Optional<Double> calculatePriceMenus(List<Menu> menus) {
		return menus.stream()
				.map(Menu::getPrice)
				.reduce(Double::sum);
	}

	/**
	 * Complete a payment.
	 * 
	 * @param paypal
	 * @return status
	 */
	@Override
	public Map<String, Object> completePayment(Paypal paypal) {
		Map<String, Object> response = new HashMap<>();

		Payment payment = getPayment(paypal);
		try {
			PaymentExecution paymentExecution = getPaymentExecution(paypal);
			APIContext apiContext = getApiContext();
			Payment createdPayment = payment.execute(apiContext, paymentExecution);
			if (null != createdPayment) {
				setStatusCommand(paypal, createdPayment);
				response.put(Constants.STATUS, Constants.SUCCESS);
				response.put(Constants.PAYMENT, createdPayment);
			}
		} catch (PayPalRESTException e) {
			LOGGER.error(e.getDetails().toString(), e);
		}
		return response;
	}

	private APIContext getApiContext() {
		return new APIContext(
				configurationService.getPaypalClientId(),
				configurationService.getPaypalClientSecret(), Constants.SANDBOX);
	}

	private Payment getPayment(Paypal paypal) {
		Payment payment = new Payment();
		payment.setId(paypal.getPaymentId());
		return payment;
	}

	private PaymentExecution getPaymentExecution(Paypal paypal) {
		PaymentExecution paymentExecution = new PaymentExecution();
		paymentExecution.setPayerId(paypal.getPayerID());
		return paymentExecution;
	}

	private void setStatusCommand(Paypal paypal, Payment payment) {
		Optional<CommandDTO> result = commandService.findByPaymentId(paypal.getPaymentId());
		if (result.isPresent()) {
			CommandDTO commandDTO = result.get();
			commandDTO.setOrderStatus(Constants.PAID);
			commandDTO.setDate(ZonedDateTime.now(ZoneId.of("Europe/Paris")));
			commandDTO.setSaleId(payment.getTransactions().get(0).getRelatedResources().get(0).getSale().getId());
			commandDTO.setCustomerId(commandDTO.getCustomerId());
			commandService.update(commandDTO);
		}
	}

	/**
	 * Refund a user.
	 * @param commandId
	 * @return status
	 */
	@Override
	public Map<String, Object> refundPayment(Long commandId) {
		Map<String, Object> response = new HashMap<>();
		Optional<CommandDTO> commandDTO = commandService.findOne(commandId);
		if (commandDTO.isPresent()) {
			CommandDTO result = commandDTO.get();
			Refund refund = new Refund();

			Sale sale = new Sale();
			sale.setId(result.getSaleId());

			Amount amount = new Amount();
			amount.setTotal(String.valueOf(result.getPrice()));
			amount.setCurrency(Constants.EUR);
			refund.setAmount(amount);

			RefundRequest refundRequest = new RefundRequest();

			try {
				APIContext apiContext = getApiContext();
				sale.refund(apiContext, refundRequest);
				commandService.delete(commandId);
				response.put(Constants.STATUS, Constants.SUCCESS);
			} catch (PayPalRESTException e) {
				LOGGER.error(e.getDetails().toString(), e);
			}
		}

		return response;
	}
}
