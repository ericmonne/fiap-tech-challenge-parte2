package com.fiap.tech_challenge.parte1.ms_users.application.usecase.restaurant;

import com.fiap.tech_challenge.parte1.ms_users.application.port.output.restaurant.RestaurantGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.ForbiddenOperationException;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.RestaurantNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteRestaurantUseCaseImplTest {

    @Mock
    private RestaurantGateway restaurantGateway;

    @InjectMocks
    private DeleteRestaurantUseCaseImpl deleteRestaurantUseCase;

    private final UUID restaurantId = UUID.randomUUID();
    private final UUID userId = UUID.randomUUID();
    private final UUID otherUserId = UUID.randomUUID();

    @Test
    void shouldDeleteRestaurantWhenValidOwner() {
        when(restaurantGateway.existsById(restaurantId)).thenReturn(true);
        when(restaurantGateway.isRestaurantOwnedByUser(restaurantId, userId)).thenReturn(true);

        deleteRestaurantUseCase.execute(restaurantId, userId);

        verify(restaurantGateway).delete(restaurantId);
    }

    @Test
    void shouldThrowRestaurantNotFoundExceptionWhenRestaurantDoesNotExist() {
        when(restaurantGateway.existsById(restaurantId)).thenReturn(false);

        assertThatThrownBy(() -> deleteRestaurantUseCase.execute(restaurantId, userId))
                .isInstanceOf(RestaurantNotFoundException.class)
                .hasMessageContaining("Restaurante não encontrado: " + restaurantId);

        verify(restaurantGateway, never()).isRestaurantOwnedByUser(any(), any());
        verify(restaurantGateway, never()).delete(any());
    }

    @Test
    void shouldThrowForbiddenOperationExceptionWhenNotOwner() {
        when(restaurantGateway.existsById(restaurantId)).thenReturn(true);
        when(restaurantGateway.isRestaurantOwnedByUser(restaurantId, otherUserId)).thenReturn(false);

        assertThatThrownBy(() -> deleteRestaurantUseCase.execute(restaurantId, otherUserId))
                .isInstanceOf(ForbiddenOperationException.class)
                .hasMessageContaining("Você não tem permissão para atualizar este restaurante.");

        verify(restaurantGateway, never()).delete(any());
    }

    @Test
    void shouldVerifyCorrectOrderOfValidations() {
        UUID nonExistentId = UUID.randomUUID();
        when(restaurantGateway.existsById(nonExistentId)).thenReturn(false);

        assertThatThrownBy(() -> deleteRestaurantUseCase.execute(nonExistentId, userId))
                .isInstanceOf(RestaurantNotFoundException.class);

        verify(restaurantGateway, never()).isRestaurantOwnedByUser(any(), any());
        verify(restaurantGateway, never()).delete(any());
    }
}