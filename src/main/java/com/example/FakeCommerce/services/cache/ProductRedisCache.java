package com.example.FakeCommerce.services.cache;

import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.example.FakeCommerce.dtos.GetProductResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductRedisCache {

    //stringredistemplate
    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;
    private static final String KEY_SUMMARY = "product:summary";
    
    public Optional<GetProductResponseDto> getSummary(Long id){
        String responseJson = stringRedisTemplate.opsForValue().get(KEY_SUMMARY + id);
        if(responseJson == null)return Optional.empty();

        try{
          GetProductResponseDto response = objectMapper.readValue(responseJson, GetProductResponseDto.class);
          return Optional.of(response);
        }catch(Exception e){
             log.error("Error in parsing product summary from cache: {}", e.getMessage());
             return Optional.empty();
        }

    }
        private void putSummary(Long id, GetProductResponseDto response){
            try{
                stringRedisTemplate.opsForValue().set(KEY_SUMMARY + id, objectMapper.writeValueAsString(response));

            }catch(Exception e){
                throw new RuntimeException("Error serializing product summary to cache: " + e.getMessage());
            }

        }
    
    
}
