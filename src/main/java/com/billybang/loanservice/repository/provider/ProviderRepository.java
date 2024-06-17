package com.billybang.loanservice.repository.provider;

import com.billybang.loanservice.model.entity.provider.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepository extends JpaRepository<Provider, Integer> {

}
