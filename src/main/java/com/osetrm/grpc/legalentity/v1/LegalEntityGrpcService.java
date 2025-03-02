package com.osetrm.grpc.legalentity.v1;

import com.google.protobuf.Empty;
import com.osetrm.grpc.legalentity.LegalEntityEntity;
import com.osetrm.grpc.legalentity.LegalEntityRepository;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.quarkus.grpc.GrpcService;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.smallrye.mutiny.Uni;
import jakarta.persistence.NoResultException;

@GrpcService
public class LegalEntityGrpcService implements LegalEntityGrpc {

    private final LegalEntityRepository legalEntityRepository;

    public LegalEntityGrpcService(LegalEntityRepository legalEntityRepository) {
        this.legalEntityRepository = legalEntityRepository;
    }

    @Override
    @WithSession
    public Uni<FindByGlobalLegalEntityIdentifierResponse> findByGlobalLegalEntityIdentifier(FindByGlobalLegalEntityIdentifierRequest request) {
        return legalEntityRepository.findByGlobalLegalEntityIdentifier(request.getGlobalLegalEntityIdentifier())
                .onFailure(NoResultException.class).recoverWithNull()
                .onItem().ifNull().failWith(() -> new StatusRuntimeException(Status.NOT_FOUND))
                .map(this::toLegalEntity)
                .map(legalEntity -> FindByGlobalLegalEntityIdentifierResponse.newBuilder().setLegalEntity(legalEntity).build());
    }

    @Override
    @WithSession
    public Uni<Empty> createLegalEntity(CreateLegalEntityRequest request) {
        var entity = new LegalEntityEntity();
        entity.globalLegalEntityIdentifier = request.getLegalEntity().getGlobalLegalEntityIdentifier();
        entity.legalName = request.getLegalEntity().getLegalName();
        return legalEntityRepository.persist(entity).replaceWith(Empty.getDefaultInstance());
    }

    private LegalEntity toLegalEntity(LegalEntityEntity entity) {
        return LegalEntity.newBuilder()
                .setGlobalLegalEntityIdentifier(entity.globalLegalEntityIdentifier)
                .setLegalName(entity.legalName)
                .build();
    }

}
