package com.osetrm.grpc.legalentity;

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LegalEntityRepository implements PanacheRepositoryBase<LegalEntityEntity, Long> {

    public Uni<LegalEntityEntity> findByGlobalLegalEntityIdentifier(String globalLegalEntityIdentifier){
        return find("globalLegalEntityIdentifier", globalLegalEntityIdentifier).singleResult();
    }

}
