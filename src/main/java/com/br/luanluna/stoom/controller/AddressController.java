package com.br.luanluna.stoom.controller;

import com.br.luanluna.stoom.exception.GenericAddressException;
import com.br.luanluna.stoom.exception.AddressNotFoundException;
import com.br.luanluna.stoom.model.Address;
import com.br.luanluna.stoom.model.ErrorResponse;
import com.br.luanluna.stoom.model.Pagination;
import com.br.luanluna.stoom.service.AddressService;
import com.br.luanluna.stoom.util.ControllerUtil;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;
import java.math.BigInteger;


@EnableSwagger2
@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @RequestMapping(method = RequestMethod.POST)
    @ApiResponses({
            @ApiResponse(code = 201, response = Address.class, message = "Created"),
            @ApiResponse(code = 400, response = ErrorResponse.class, message = "Bad Request"),
            @ApiResponse(code = 404, response = ErrorResponse.class, message = "Not Found"),
            @ApiResponse(code = 500, response = ErrorResponse.class, message = "Internal Server Error")
    })
    public ResponseEntity<?> create(@Valid @RequestBody(required = true) Address address, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String[] errors = ControllerUtil.extractErrors(bindingResult);
            return new ResponseEntity<>(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }

        try {
            return new ResponseEntity<>(addressService.create(address), HttpStatus.CREATED);

        } catch (GenericAddressException genericAddressException) {
            return new ResponseEntity<>(new ErrorResponse(genericAddressException.getReasons()),
                    HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(new String[]{"Internal server error"}),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    @ApiResponses({
            @ApiResponse(code = 200, response = Address.class, message = "Ok"),
            @ApiResponse(code = 400, response = ErrorResponse.class, message = "Bad Request"),
            @ApiResponse(code = 404, response = ErrorResponse.class, message = "Not Found"),
            @ApiResponse(code = 500, response = ErrorResponse.class, message = "Internal Server Error")
    })
    public ResponseEntity<?> update(@PathVariable(required = true) BigInteger id,
                                    @Valid @RequestBody(required = true) Address address,
                                    BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String[] errors = ControllerUtil.extractErrors(bindingResult);
            return new ResponseEntity<>(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }

        try {
            if (id.compareTo(BigInteger.ZERO) < 1) {
                return new ResponseEntity<>(new String[]{"Not found"}, HttpStatus.NOT_FOUND);
            } else {
                address.setId(id);
            }

            return new ResponseEntity<>(addressService.update(address), HttpStatus.OK);

        } catch (AddressNotFoundException addressNotFoundException) {
            return new ResponseEntity<>(new ErrorResponse(new String[]{"Not found"}), HttpStatus.NOT_FOUND);

        } catch (GenericAddressException genericAddressException) {
            return new ResponseEntity<>(new ErrorResponse(genericAddressException.getReasons()), HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(new String[]{"Internal server error"}), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 400, response = ErrorResponse.class, message = "Bad Request"),
            @ApiResponse(code = 404, response = ErrorResponse.class, message = "Not Found"),
            @ApiResponse(code = 500, response = ErrorResponse.class, message = "Internal Server Error")
    })
    public ResponseEntity<?> delete(@PathVariable(required = true) BigInteger id) {

        try {
            if (id.compareTo(BigInteger.ZERO) < 1) {
                return new ResponseEntity<>(new String[]{"Not found"}, HttpStatus.NOT_FOUND);
            }

            addressService.delete(id);
            return ResponseEntity.ok().build();

        } catch (AddressNotFoundException addressNotFoundException) {
            return new ResponseEntity<>(new ErrorResponse(new String[]{"Not found"}), HttpStatus.NOT_FOUND);

        } catch (GenericAddressException genericAddressException) {
            return new ResponseEntity<>(new ErrorResponse(genericAddressException.getReasons()), HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(new String[]{"Internal server error"}), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    @ApiResponses({
            @ApiResponse(code = 200, response = Address.class, message = "Ok"),
            @ApiResponse(code = 400, response = ErrorResponse.class, message = "Bad Request"),
            @ApiResponse(code = 404, response = ErrorResponse.class, message = "Not Found"),
            @ApiResponse(code = 500, response = ErrorResponse.class, message = "Internal Server Error")
    })
    public ResponseEntity<?> get(@PathVariable(required = true) BigInteger id) {

        try {
            if (id.compareTo(BigInteger.ZERO) < 1) {
                return new ResponseEntity<>(new String[]{"Not found"}, HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(addressService.get(id), HttpStatus.OK);

        } catch (AddressNotFoundException addressNotFoundException) {
            return new ResponseEntity<>(new ErrorResponse(new String[]{"Not found"}), HttpStatus.NOT_FOUND);

        } catch (GenericAddressException genericAddressException) {
            return new ResponseEntity<>(new ErrorResponse(genericAddressException.getReasons()), HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(new String[]{"Internal server error"}), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiResponses({
            @ApiResponse(code = 200, response = Address.class, responseContainer = "List", message = "Ok"),
            @ApiResponse(code = 400, response = ErrorResponse.class, message = "Bad Request"),
            @ApiResponse(code = 404, response = ErrorResponse.class, message = "Not Found"),
            @ApiResponse(code = 500, response = ErrorResponse.class, message = "Internal Server Error")
    })
    public ResponseEntity<?> list(@RequestParam(defaultValue = "0") Integer page,
                                  @RequestParam(defaultValue = "10") Integer size,
                                  @RequestParam(defaultValue = "id") String sortBy,
                                  @RequestParam(defaultValue = "false") boolean desc) {

        try {
            Pagination pagination = new Pagination(page, size, sortBy, desc);
            return new ResponseEntity<>(addressService.getAll(pagination), HttpStatus.OK);

        } catch (AddressNotFoundException addressNotFoundException) {
            return new ResponseEntity<>(new ErrorResponse(new String[]{"Not found"}), HttpStatus.NOT_FOUND);

        } catch (GenericAddressException genericAddressException) {
            return new ResponseEntity<>(new ErrorResponse(genericAddressException.getReasons()), HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(new String[]{"Internal server error"}), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
