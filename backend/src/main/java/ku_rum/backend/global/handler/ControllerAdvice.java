package ku_rum.backend.global.handler;

import ku_rum.backend.global.exception.building.BuildingNotFoundException;
import ku_rum.backend.global.exception.building.BuildingNotRegisteredException;
import ku_rum.backend.global.exception.department.NoSuchDepartmentException;
import ku_rum.backend.global.exception.user.DuplicateEmailException;
import ku_rum.backend.global.exception.user.DuplicateStudentIdException;
import ku_rum.backend.global.response.BaseErrorResponse;
import ku_rum.backend.global.response.status.BaseExceptionResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class ControllerAdvice {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public BaseErrorResponse handleServerException(final Exception e) {
        log.error(e.getMessage());
        return new BaseErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateEmailException.class)
    public BaseErrorResponse handleDuplicateEmailException(final DuplicateEmailException e) {
        log.error(e.getMessage());
        return new BaseErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateStudentIdException.class)
    public BaseErrorResponse handleDuplicateStudentIdException(final DuplicateStudentIdException e) {
        log.error(e.getMessage());
        return new BaseErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchDepartmentException.class)
    public BaseErrorResponse handleNoSuchDepartmentException(final NoSuchDepartmentException e) {
        log.error(e.getMessage());
        return new BaseErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(BuildingNotRegisteredException.class)
    public BaseErrorResponse handleNoBuildingRegisteredException(final BuildingNotRegisteredException e) {
        return new BaseErrorResponse(HttpStatus.NO_CONTENT, e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BuildingNotFoundException.class)
    public BaseErrorResponse handleNoBuildingFoundException(final BaseExceptionResponseStatus e){
        return new BaseErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }


}