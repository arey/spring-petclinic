package org.springframework.samples.petclinic.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Functions that are invoked by the LLM will use this bean to query the system of record
 * for information such as listing owners and vets, or adding pets to an owner.
 *
 * @author Oded Shopen
 */
@Service
public class ChatFunctionDataProvider {

	private static final Logger LOG = LoggerFactory.getLogger(ChatFunctionDataProvider.class);

	private final OwnerRepository ownerRepository;

	public ChatFunctionDataProvider(OwnerRepository ownerRepository) {
		this.ownerRepository = ownerRepository;
	}

	public AddedPetResponse addPetToOwner(AddPetRequest request) {
		Owner owner = ownerRepository.findById(request.ownerId());
		owner.addPet(request.pet());
		this.ownerRepository.save(owner);
		return new AddedPetResponse(owner);
	}

	public OwnerQueryResponse queryOwners(OwnerQueryRequest request) {
		LOG.debug("Call function queryOwners with lastName='{}'", request.lastName());
		Pageable pageable = PageRequest.of(0, 10);
		return new OwnerQueryResponse(ownerRepository.findByLastName(request.lastName(), pageable).toList());
	}
}


record AddPetRequest(Pet pet, Integer ownerId) {
}

record OwnerQueryRequest(String lastName) {
}

record OwnerQueryResponse(List<Owner> owners) {
}

record AddedPetResponse(Owner owner) {
}

record VetResponse(List<String> vet) {
}

record VetRequest(Vet vet) {

}

