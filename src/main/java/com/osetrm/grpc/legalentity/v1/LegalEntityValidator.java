package com.osetrm.grpc.legalentity.v1;

import com.google.protobuf.Any;
import com.google.rpc.BadRequest;
import com.google.rpc.Code;
import com.google.rpc.Status;
import io.grpc.protobuf.StatusProto;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class LegalEntityValidator {

    //TODO Use ValidationMessages

    public void validate(LegalEntity legalEntity) {
        List<BadRequest.FieldViolation> violations = new ArrayList<>();

        if (legalEntity.getGlobalLegalEntityIdentifier().length() != 20) {
            violations.add(
                    BadRequest.FieldViolation.newBuilder()
                            .setField("globalLegalEntityIdentifier")
                            .setDescription("Global Legal Entity Identifier must be exactly 20 characters.")
                            .build()
            );
        }

        if (legalEntity.getLegalName().isBlank()) {
            violations.add(
                    BadRequest.FieldViolation.newBuilder()
                            .setField("legalName")
                            .setDescription("Legal Name cannot be empty.")
                            .build()
            );
        }

        if (!violations.isEmpty()) {
            BadRequest badRequest = BadRequest.newBuilder()
                    .addAllFieldViolations(violations)
                    .build();
            Status status = Status.newBuilder()
                    .setCode(Code.INVALID_ARGUMENT_VALUE) // gRPC code: 3
                    .setMessage("Validation error(s) have occurred")
                    .addDetails(Any.pack(badRequest))
                    .build();
            throw StatusProto.toStatusRuntimeException(status);
        }
    }

}
