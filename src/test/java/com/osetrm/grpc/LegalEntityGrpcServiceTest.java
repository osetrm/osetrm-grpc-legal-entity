package com.osetrm.grpc;

import com.osetrm.grpc.legalentity.FindByGlobalLegalEntityIdentifierRequest;
import com.osetrm.grpc.legalentity.LegalEntityGrpc;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.quarkus.grpc.GrpcClient;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class LegalEntityGrpcServiceTest {

    @GrpcClient
    LegalEntityGrpc legalEntityGrpc;

    @Test
    void findByGlobalLegalEntityIdentifier() {
        var request = FindByGlobalLegalEntityIdentifierRequest.newBuilder()
                .setGlobalLegalEntityIdentifier("08C2IQ0VM1B2PBTVHC56")
                .build();
        var response = legalEntityGrpc.findByGlobalLegalEntityIdentifier(request).await().atMost(Duration.ofSeconds(5));
        assertEquals("08C2IQ0VM1B2PBTVHC56", response.getLegalEntity().getGlobalLegalEntityIdentifier());
    }

    @Test
    void findByGlobalLegalEntityIdentifierNotFound() {
        var request = FindByGlobalLegalEntityIdentifierRequest.newBuilder()
                .setGlobalLegalEntityIdentifier("RANDOM")
                .build();
        StatusRuntimeException thrown = Assertions.assertThrows(StatusRuntimeException.class, () -> {
            legalEntityGrpc.findByGlobalLegalEntityIdentifier(request).await().atMost(Duration.ofSeconds(5));
        });
        assertEquals(Status.NOT_FOUND.getCode(), thrown.getStatus().getCode());
    }

}
