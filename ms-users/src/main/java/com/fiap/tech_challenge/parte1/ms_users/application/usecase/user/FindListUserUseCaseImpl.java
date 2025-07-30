package com.fiap.tech_challenge.parte1.ms_users.application.usecase.user;

import com.fiap.tech_challenge.parte1.ms_users.application.port.input.user.controller.FindListUserUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.address.AddressGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.UserGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Address;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class FindListUserUseCaseImpl implements FindListUserUseCase {

    private final UserGateway userGateway;
    private final AddressGateway addressGateway;

    public FindListUserUseCaseImpl(UserGateway userGateway, AddressGateway addressGateway) {
        this.userGateway = userGateway;
        this.addressGateway = addressGateway;
    }

    @Override
    public List<User> execute(int size, int page) {
        List<User> listUsers = getUserList(size, page);
        Map<String, List<Address>> addressByUserMap = getAddressByUserMap(listUsers);
        linkAddressToTheCorrectUser(listUsers, addressByUserMap);
        return listUsers;
    }

    private void linkAddressToTheCorrectUser(List<User> listUsers, Map<String, List<Address>> addressByUserMap) {
        listUsers.forEach(user -> user.setAddress(addressByUserMap.getOrDefault(user.getId().toString(), List.of())));
    }

    private Map<String, List<Address>> getAddressByUserMap(List<User> listUsers) {
        Set<UUID> userIdSet = getUserIdSet(listUsers);
        return getAddressByUserMap(userIdSet);
    }

    private List<User> getUserList(int size, int page) {
        var offset = (page - 1) * size;
        return userGateway.findAll(size, offset);
    }

    private Set<UUID> getUserIdSet(List<User> listUsers) {
        return listUsers.stream().map(User::getId).collect(Collectors.toSet());
    }

    private Map<String, List<Address>> getAddressByUserMap(Set<UUID> userIdSet) {
        List<Address> addressList = addressGateway.findAllByUserIds(userIdSet);
        return addressList.stream()
                .collect(Collectors.groupingBy(Address::getUserId));
    }
}
