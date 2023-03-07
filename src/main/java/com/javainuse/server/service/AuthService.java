package com.javainuse.server.service;

import com.javainuse.generalModel.OwenUser;
import com.javainuse.server.repository.auth.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    AuthRepository authRepository;

    /**
     * Проверка существования токена у юзера
     * Если существует, то обновляем (в случае повторной авторизации)
     * Если нет, то сохраняем
     * @param user
     * @param token
     */
    public void saveToken(OwenUser user, String token) {
        if (authRepository.existToken(user)) {
            authRepository.updateToken(user, token);
        } else {
            authRepository.saveOwenToken(user.getUser_id(), token);
        }
    }
}
