package fr.esgi.service.dto;


import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * 
 * A DTO representing a command.
 *
 */
public class CommandDTO {

	private Long id;

	private String orderStatus;

	private LocalDate date;

	private Long customerId;

	private List<MenuDTO> menusDTO;
	
	private List<ProductDTO> productsDTO;
	
	private String paymentId;
	
	public CommandDTO() {
		// Empty constructor needed for Jackson.
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public List<MenuDTO> getMenusDTO() {
		return menusDTO;
	}

	public void setMenusDTO(List<MenuDTO> menusDTO) {
		this.menusDTO = menusDTO;
	}

	public List<ProductDTO> getProductsDTO() {
		return productsDTO;
	}

	public void setProductsDTO(List<ProductDTO> productsDTO) {
		this.productsDTO = productsDTO;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(customerId, date, id, menusDTO, orderStatus, paymentId, productsDTO);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommandDTO other = (CommandDTO) obj;
		return Objects.equals(customerId, other.customerId) && Objects.equals(date, other.date)
				&& Objects.equals(id, other.id) && Objects.equals(menusDTO, other.menusDTO)
				&& Objects.equals(orderStatus, other.orderStatus) && Objects.equals(paymentId, other.paymentId)
				&& Objects.equals(productsDTO, other.productsDTO);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CommandDTO [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (orderStatus != null) {
			builder.append("orderStatus=");
			builder.append(orderStatus);
			builder.append(", ");
		}
		if (date != null) {
			builder.append("date=");
			builder.append(date);
			builder.append(", ");
		}
		if (customerId != null) {
			builder.append("customerId=");
			builder.append(customerId);
			builder.append(", ");
		}
		if (menusDTO != null) {
			builder.append("menusDTO=");
			builder.append(menusDTO);
			builder.append(", ");
		}
		if (productsDTO != null) {
			builder.append("productsDTO=");
			builder.append(productsDTO);
			builder.append(", ");
		}
		if (paymentId != null) {
			builder.append("paymentId=");
			builder.append(paymentId);
		}
		builder.append("]");
		return builder.toString();
	}
}
