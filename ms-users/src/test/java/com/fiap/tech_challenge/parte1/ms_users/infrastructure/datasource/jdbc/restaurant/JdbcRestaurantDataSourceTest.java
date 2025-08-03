package com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.restaurant;

import com.fiap.tech_challenge.parte1.ms_users.domain.model.CuisineType;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Restaurant;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.mapper.RestaurantMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JdbcRestaurantDataSourceTest {

    @Mock
    private JdbcRestaurantRepository jdbcRestaurantRepository;

    @Mock
    private RestaurantMapper restaurantMapper;

    @InjectMocks
    private JdbcRestaurantDataSource dataSource;

    private UUID testRestaurantId;
    private UUID testUserId;
    private Restaurant testRestaurant;
    private JdbcRestaurantEntity testJdbcEntity;

    @BeforeEach
    void setUp() {
        testRestaurantId = UUID.randomUUID();
        testUserId = UUID.randomUUID();

        User owner = new User();
        owner.setId(testUserId);

        testRestaurant = new Restaurant();
        testRestaurant.setId(testRestaurantId);
        testRestaurant.setName("Test Restaurant");
        testRestaurant.setCuisineType(CuisineType.ITALIANA);
        testRestaurant.setUser(owner);

        testJdbcEntity = new JdbcRestaurantEntity();
        testJdbcEntity.setId(testRestaurantId);
        testJdbcEntity.setName("Test Restaurant");
        testJdbcEntity.setCuisineType(CuisineType.ITALIANA);
        testJdbcEntity.setUserId(testUserId);
    }

    @Test
    void createRestaurant_ShouldConvertAndSaveEntity() {
        when(restaurantMapper.toJdbcRestaurantEntity(testRestaurant)).thenReturn(testJdbcEntity);
        when(jdbcRestaurantRepository.save(testJdbcEntity)).thenReturn(testRestaurantId);

        UUID resultId = dataSource.createRestaurant(testRestaurant);

        assertEquals(testRestaurantId, resultId);
        verify(restaurantMapper).toJdbcRestaurantEntity(testRestaurant);
        verify(jdbcRestaurantRepository).save(testJdbcEntity);
    }

    @Test
    void findById_ShouldReturnRestaurantWhenExists() {
        when(jdbcRestaurantRepository.findById(testRestaurantId))
                .thenReturn(Optional.of(testRestaurant));

        Optional<Restaurant> result = dataSource.findById(testRestaurantId);

        assertTrue(result.isPresent());
        assertEquals(testRestaurantId, result.get().getId());
        verify(jdbcRestaurantRepository).findById(testRestaurantId);
    }

    @Test
    void findById_ShouldReturnEmptyWhenNotExists() {
        when(jdbcRestaurantRepository.findById(testRestaurantId))
                .thenReturn(Optional.empty());

        Optional<Restaurant> result = dataSource.findById(testRestaurantId);

        assertTrue(result.isEmpty());
        verify(jdbcRestaurantRepository).findById(testRestaurantId);
    }

    @Test
    void update_ShouldConvertAndUpdateEntity() {
        when(restaurantMapper.toJdbcRestaurantEntity(testRestaurant)).thenReturn(testJdbcEntity);
        doNothing().when(jdbcRestaurantRepository).update(testJdbcEntity);

        dataSource.update(testRestaurant);

        verify(restaurantMapper).toJdbcRestaurantEntity(testRestaurant);
        verify(jdbcRestaurantRepository).update(testJdbcEntity);
    }

    @Test
    void existsById_ShouldDelegateToRepository() {
        when(jdbcRestaurantRepository.existsById(testRestaurantId)).thenReturn(true);

        boolean exists = dataSource.existsById(testRestaurantId);

        assertTrue(exists);
        verify(jdbcRestaurantRepository).existsById(testRestaurantId);
    }

    @Test
    void delete_ShouldDelegateToRepository() {
        doNothing().when(jdbcRestaurantRepository).delete(testRestaurantId);

        dataSource.delete(testRestaurantId);

        verify(jdbcRestaurantRepository).delete(testRestaurantId);
    }

    @Test
    void findAllRestaurantsByUserId_ShouldDelegateToRepository() {
        int size = 10;
        int offset = 0;
        List<Restaurant> expectedList = List.of(testRestaurant);

        when(jdbcRestaurantRepository.findAllByUserId(testUserId, size, offset))
                .thenReturn(expectedList);

        List<Restaurant> result = dataSource.findAllRestaurantsByUserId(testUserId, size, offset);

        assertEquals(expectedList, result);
        verify(jdbcRestaurantRepository).findAllByUserId(testUserId, size, offset);
    }

    @Test
    void isRestaurantOwnedByUser_ShouldReturnTrueWhenCountGreaterThanZero() {
        when(jdbcRestaurantRepository.countByUserIdAndRestaurantId(testRestaurantId, testUserId))
                .thenReturn(1);

        boolean isOwned = dataSource.isRestaurantOwnedByUser(testRestaurantId, testUserId);

        assertTrue(isOwned);
        verify(jdbcRestaurantRepository).countByUserIdAndRestaurantId(testRestaurantId, testUserId);
    }

    @Test
    void isRestaurantOwnedByUser_ShouldReturnFalseWhenCountIsZero() {
        when(jdbcRestaurantRepository.countByUserIdAndRestaurantId(testRestaurantId, testUserId))
                .thenReturn(0);

        boolean isOwned = dataSource.isRestaurantOwnedByUser(testRestaurantId, testUserId);

        assertFalse(isOwned);
        verify(jdbcRestaurantRepository).countByUserIdAndRestaurantId(testRestaurantId, testUserId);
    }

}