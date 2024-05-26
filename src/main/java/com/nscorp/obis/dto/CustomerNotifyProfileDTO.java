package com.nscorp.obis.dto;

import com.nscorp.obis.domain.DayOfWeek;
import com.nscorp.obis.domain.Shift;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class CustomerNotifyProfileDTO extends AuditInfoDTO {

	@Digits(integer=15, fraction=0, message="Notify Profile Id should not have more than 15 digits. ")
	@Min(value = 0, message = "Notify Profile Id value must be greater than or equal to 0")
	@Schema(required = true,description="The Id of the notify Profile.", example="978284241407")
    private Long notifyProfileId;
	
    @Min(value = 0, message = "Notify Profile Id value must be greater than or equal to 0")
    @Digits(integer=15, fraction=0, message="Notify Terminal Id should not have more than 15 digits. ")
    @Schema(required=false, description="This is notify terminal id of customer.") 
    private Long notifyTerminalId;

    @Schema(required = false,description="This is the AAR (Association of American Railroads) code that identifies this category of event.", example="SEAL")	
	private String eventCode;
    
    @Size(min=0, max=7)
	@UniqueElements(message = "Day Of Week must have unique values only.")
	@Schema(required = false,description="This defines the day of the week.")
	private List<DayOfWeek> dayOfWeek;
    
    @Size(min=0, max=3)
	@UniqueElements(message = "Shift must have unique values only.")
	@Schema(required =false,description="This identifies a specific work shift.")	
	private List<Shift> shift;
	
    @Valid
	@Schema(required=false, description="These are the customer for the associated Notify Profile") 
	CustomerDTO customer;
	
	@Valid
	@Schema(required =false,description="These are the notify Profile Methods for the associated Notify Profile")
	private List<NotifyProfileMethodDTO> notifyProfileMethods;


}
