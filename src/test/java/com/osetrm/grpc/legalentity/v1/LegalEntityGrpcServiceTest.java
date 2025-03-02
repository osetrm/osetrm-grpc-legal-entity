package com.osetrm.grpc.legalentity.v1;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.quarkus.grpc.GrpcClient;
import io.quarkus.test.junit.QuarkusTest;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    @Test
    void createLegalEntity() {
        var request = CreateLegalEntityRequest.newBuilder()
                .setLegalEntity(LegalEntity.newBuilder()
                        .setGlobalLegalEntityIdentifier(RandomStringUtils.insecure().nextAlphanumeric(20))
                        .setLegalName(RandomStringUtils.insecure().nextAlphabetic(20))
                        .build())
                .build();
        var response = legalEntityGrpc.createLegalEntity(request).await().atMost(Duration.ofSeconds(5));
        assertNotNull(response);
    }

    @Test
    void createLegalEntityFailInvalidGlobalLegalEntityIdentifier() {
        var request = CreateLegalEntityRequest.newBuilder()
                .setLegalEntity(LegalEntity.newBuilder()
                        .setGlobalLegalEntityIdentifier(RandomStringUtils.insecure().nextAlphanumeric(10))
                        .setLegalName(RandomStringUtils.insecure().nextAlphabetic(20))
                        .build())
                .build();
        StatusRuntimeException thrown = Assertions.assertThrows(StatusRuntimeException.class, () -> {
            legalEntityGrpc.createLegalEntity(request).await().atMost(Duration.ofSeconds(5));
        });
        assertEquals(Status.INVALID_ARGUMENT.getCode(), thrown.getStatus().getCode());
    }

}
