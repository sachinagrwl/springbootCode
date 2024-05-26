package com.nscorp.obis.dto;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.domain.Customer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class CorporateCustomerDetailDTO {

	
    private Long corpCustId;
    
	@NotNull(message = "Corp Primary 6 Should not be Null.")
	@Schema(required = true,description="The Corporate Primary 6 being used.", example="07126")
	@Size(min=6,max=CommonConstants.CORP_PRIMARY_6_MAX_SIZE,message="size of 'corpCust6' should be {max}")
    private String corpCust6;
	
    private List<Customer> customer;
    
}
