package br.com.macrosapi.services;

import br.com.macrosapi.dto.food.RegisterFoodDTO;
import br.com.macrosapi.model.food.Food;
import br.com.macrosapi.model.user.User;
import br.com.macrosapi.repositories.FoodRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class FoodServiceTest {

    @InjectMocks
    private FoodService foodService;

    @Mock
    private FoodRepository foodRepository;

    @Mock
    private UserService userService;

    @Mock
    private RegisterFoodDTO foodDTO;

    @Mock
    private HttpServletRequest request;

    @Mock
    private User user;

    @Mock
    private User user2;

    @Mock
    private Food food;

    @Mock
    private UUID uuid;

    @Captor
    private ArgumentCaptor<Food> foodCaptor;

    @Test
    @DisplayName("Should save new food on database")
    void testCase01() {
        // ACT
        foodService.register(foodDTO, request);

        // ASSERT
        BDDMockito.then(foodRepository).should().save(foodCaptor.capture());
        Food savedFood = foodCaptor.getValue();
        Assertions.assertEquals(savedFood.getId(), food.getId());
    }

    @Test
    @DisplayName("Should delete a food on database when food's creator is the requesting")
    void testCase02() throws IllegalAccessException {
        // ARRANGE
        BDDMockito.given(foodRepository.getReferenceById(uuid)).willReturn(food);
        BDDMockito.given(userService.getUserByHttpRequest(request)).willReturn(user);
        BDDMockito.given(food.getUser()).willReturn(user);

        // ASSERT + ACT
        Assertions.assertDoesNotThrow(() -> foodService.delete(uuid, request));
    }

    @Test
    @DisplayName("Shouldn't delete a food on database when food's creator isn't the requesting")
    void testCase03() throws IllegalAccessException {
        // ARRANGE
        BDDMockito.given(foodRepository.getReferenceById(uuid)).willReturn(food);
        BDDMockito.given(userService.getUserByHttpRequest(request)).willReturn(user);
        BDDMockito.given(user.getId()).willReturn(UUID.randomUUID());
        BDDMockito.given(food.getUser()).willReturn(user2);

        // ASSERT + ACT
        Assertions.assertThrows(IllegalAccessException.class, () -> foodService.delete(uuid, request));
    }
}