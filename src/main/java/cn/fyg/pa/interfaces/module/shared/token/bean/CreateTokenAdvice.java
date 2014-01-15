package cn.fyg.pa.interfaces.module.shared.token.bean;

import java.util.Map;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

import cn.fyg.pa.interfaces.module.shared.session.SessionUtil;
import cn.fyg.pa.interfaces.module.shared.token.annotation.CreateToken;

@Component
public class CreateTokenAdvice {
	
	@Resource
	SessionUtil sessionUtil; 

	public void after(JoinPoint jp,CreateToken createToken){
		if(createToken.mapIndex()>=0){
			@SuppressWarnings("unchecked")
			Map<String,Object> map=(Map<String, Object>) jp.getArgs()[createToken.mapIndex()];
			String token = sessionUtil.createToken();
			map.put("session_token", token);
		}
	}

}
