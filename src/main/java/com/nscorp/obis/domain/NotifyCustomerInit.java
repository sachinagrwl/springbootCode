package com.nscorp.obis.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "NTFY_CUST_INIT")
@IdClass(value = NotifyCustomerInitPrimaryKeys.class)
@EqualsAndHashCode(callSuper=false)
public class NotifyCustomerInit extends AuditInfo {

	@Id
	@Column(name = "EQ_INIT", columnDefinition = "char(4)", nullable = false)
	private String eqInit;

	@Id
	@Column(name = "CUST_ID", columnDefinition = "double", nullable = false)
	private Long custId;

	@Transient
	Set<String> eqInitList;

}