package com.fiap.tech_challenge.parte1.ms_users.application.usecase.user;

import com.fiap.tech_challenge.parte1.ms_users.application.port.input.user.FindListUserUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.address.AddressGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.UserGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Address;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;

import java.util.*;
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
        Map<UUID, List<Address>> addressByUserMap = getAddressByUserMap(listUsers);
        linkAddressToTheCorrectUser(listUsers, addressByUserMap);
        return listUsers;
    }

    private void linkAddressToTheCorrectUser(List<User> listUsers, Map<UUID, List<Address>> addressByUserMap) {
        listUsers.forEach(user -> user.setAddress(
                addressByUserMap.getOrDefault(user.getId(), List.of())
        ));
    }

    private Map<UUID, List<Address>> getAddressByUserMap(List<User> users) {
        Set<UUID> userIds = users.stream().map(User::getId).collect(Collectors.toSet());
        if (userIds.isEmpty()) {
            return Collections.emptyMap();
        }
        List<Address> addresses = addressGateway.findAllByUserIds(userIds);
        return addresses.stream().collect(Collectors.groupingBy(Address::getUserId));
    }


    private List<User> getUserList(int size, int page) {
        var offset = (page - 1) * size;
        return userGateway.findAll(size, offset);
    }

}
