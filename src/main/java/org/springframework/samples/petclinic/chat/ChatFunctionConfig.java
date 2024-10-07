package org.springframework.samples.petclinic.chat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration
class ChatFunctionConfig {

	@Bean
	@Description("""
		Query the owners by last name, the owner information include owner id, address, telephone, city, first name and last name.
		The owner also include the pets information, include the pet name, pet type and birth.
		The pet includes serveral visit record, includes the visit name and visit date.
		""")
	public Function<OwnerQueryRequest, OwnerQueryResponse> queryOwners(ChatFunctionDataProvider dataProvider) {
		return dataProvider::queryOwners;
	}

	@Bean
	@Description("""
		Add a pet with the specified petTypeId, to an owner identified by the ownerId.
		The allowed Pet types IDs are only: 1=cat 2=dog 3=lizard 4=snake 5=bird 6=hamster
		""")
	public Function<AddPetRequest, AddedPetResponse> addPetToOwner(ChatFunctionDataProvider dataProvider) {
		return dataProvider::addPetToOwner;
	}

}


