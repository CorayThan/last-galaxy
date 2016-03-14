package com.corinthgames.lastgalaxy.annotations;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.corinthgames.lastgalaxy.models.User;
import com.corinthgames.lastgalaxy.models.UserDetailsUser;

/**
 * Resolves method arguments in controllers using the request's spring security principal into a
 * user
 *
 * Added to spring's listeners in spring config
 *
 * @author NWESTL
 *
 */
public class UserHandlerResolver implements HandlerMethodArgumentResolver {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserHandlerResolver.class);

	/**
	 * Checks that the parameter is a User annotated with @LoggedInUser
	 */
	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
		return methodParameter.getParameterAnnotation(LoggedInUser.class) != null && methodParameter.getParameterType().equals(User.class);
	}

	/**
	 * resolves the user into a user e-mail using the web request's spring principal
	 * If there is no logged in user the user will be null
	 * Spring will have previously called supportsParameter before calling this resolve argument
	 */
	@Override
	public User resolveArgument(MethodParameter methodParameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) {

		Principal principal = webRequest.getUserPrincipal();
		UserDetailsUser userToReturn = principal == null ? null : (UserDetailsUser) ((Authentication) principal).getPrincipal();

		//If a non-null user is required, and user is null, throw an exception
		if (methodParameter.getParameterAnnotation(LoggedInUser.class).required() == true
				&& userToReturn == null) {
			LOGGER.warn("The authenticated user cannot be null for the request: " + webRequest.toString());
			throw new RuntimeException("Anonymous access is not allowed for this request");
		}

		return userToReturn.getUser();
	}
}