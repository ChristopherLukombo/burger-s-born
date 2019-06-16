package fr.esgi.service.dto;

import java.util.Objects;

import javax.validation.constraints.NotNull;

/**
 * A DTO representing a Paypal account.
 */
public class Paypal {

	@NotNull
	private String paymentId;
	
	@NotNull
	private String payerID;
	
	public Paypal() {
		// Empty constructor needed for Jackson.
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getPayerID() {
		return payerID;
	}

	public void setPayerID(String payerID) {
		this.payerID = payerID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(payerID, paymentId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Paypal other = (Paypal) obj;
		return Objects.equals(payerID, other.payerID) && Objects.equals(paymentId, other.paymentId);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Paypal [");
		if (paymentId != null) {
			builder.append("paymentId=");
			builder.append(paymentId);
			builder.append(", ");
		}
		if (payerID != null) {
			builder.append("payerID=");
			builder.append(payerID);
		}
		builder.append("]");
		return builder.toString();
	}
}
