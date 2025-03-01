package com.osetrm.grpc.legalentity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.Objects;


@Entity(name = "LegalEntity")
@Table(name = "legal_entity")
public class LegalEntityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "legal_entity_id")
    public Long legalEntityId;

    @Column(name = "global_legal_entity_identifier")
    @NotEmpty(message = "{LegalEntity.globalLegalEntityIdentifier.required}")
    public String globalLegalEntityIdentifier;

    @Column(name = "legal_name")
    @NotEmpty(message = "{LegalEntity.legalName.required}")
    public String legalName;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LegalEntityEntity that = (LegalEntityEntity) o;
        return Objects.equals(legalEntityId, that.legalEntityId) && Objects.equals(globalLegalEntityIdentifier, that.globalLegalEntityIdentifier) && Objects.equals(legalName, that.legalName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(legalEntityId, globalLegalEntityIdentifier, legalName);
    }

    @Override
    public String toString() {
        return "LegalEntityEntity{" +
                "legalEntityId=" + legalEntityId +
                ", globalLegalEntityIdentifier='" + globalLegalEntityIdentifier + '\'' +
                ", legalName='" + legalName + '\'' +
                '}';
    }

}