package com.nscorp.obis.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "TKR_GRP")
@EqualsAndHashCode(callSuper = false)
public class TruckerGroup extends AuditInfo {

    @Id
    @Column(name = "TKR_GRP_CD", columnDefinition = "char(10)", nullable = false)
    private String truckerGroupCode;

    @Column(name = "TKR_GRP_DESC", columnDefinition = "char(40)", nullable = true)
    private String truckerGroupDesc;

    @Column(name = "SETUP_SCHEMA", columnDefinition = "char(8)", nullable = true)
    private String setupSchema;

    public TruckerGroup(String uversion, String createUserId, Timestamp createDateTime, String updateUserId, Timestamp updateDateTime, String updateExtensionSchema, String truckerGroupCode, String truckerGroupDesc, String setupSchema) {
        super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
        this.truckerGroupCode = truckerGroupCode;
        this.truckerGroupDesc = truckerGroupDesc;
        this.setupSchema = setupSchema;
    }

    public TruckerGroup() {
        super();
    }

    public String getTruckerGroupCode() {
        if(truckerGroupCode != null){
            return truckerGroupCode.trim();
        } else{
            return truckerGroupCode;
        }
    }

    public void setTruckerGroupCode(String truckerGroupCode) {
        if(truckerGroupCode != null) {
            this.truckerGroupCode = truckerGroupCode.toUpperCase();
        } else{
            this.truckerGroupCode = truckerGroupCode;
        }
    }

    public String getTruckerGroupDesc() {
        if(truckerGroupDesc != null){
            return truckerGroupDesc.trim();
        } else{
            return truckerGroupDesc;
        }
    }

    public void setTruckerGroupDesc(String truckerGroupDesc) {
        if(truckerGroupDesc != null){
            this.truckerGroupDesc = truckerGroupDesc.toUpperCase();
        } else{
            this.truckerGroupDesc = truckerGroupDesc;
        }
    }

    public String getSetupSchema() {
        if(setupSchema != null){
            return setupSchema.trim();
        } else{
            return setupSchema;
        }
    }

    public void setSetupSchema(String setupSchema) {
        if(setupSchema != null){
            this.setupSchema = setupSchema.toUpperCase();
        } else{
            this.setupSchema = setupSchema;
        }
    }
}
