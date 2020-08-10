/*
 *       Copyright© (2020) ICBC Co., Ltd.
 *
 *       This file is part of did-http-service.
 *
 *       did-http-service is free software: you can redistribute it and/or modify
 *       it under the terms of the GNU Lesser General Public License as published by
 *       the Free Software Foundation, either version 3 of the License, or
 *       (at your option) any later version.
 *
 *       did-http-service is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU Lesser General Public License for more details.
 *
 *       You should have received a copy of the GNU Lesser General Public License
 *       along with did-http-service.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.icbc.did.httpservice.controller;



import com.icbc.did.httpservice.constant.DidServiceEndpoint;
import com.icbc.did.httpservice.protocol.response.HttpResponseData;
import com.icbc.did.httpservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


/**
 * @Author: xuzj01@sdc.icbc.com.cn
 * @Description: Transaction Controller - to create encodedTransaction and send to Chain.
 * @File: InputArg
 * @Version: 1.0.0
 * @Date: 2019/12/15 18:45
 */

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = DidServiceEndpoint.API_ROOT)
public class TransactionController {

    @Autowired
    private TransactionService transactionService;


    /**
     * Create an Encoded Transaction.
     *
     * @param encodeTransactionJsonArgs the json format args. It should contain two keys:
     * inputParams (including all business related params as well as signatures if required), and
     * functionName. Hereafter, functionName will decide which DID SDK method to engage, and
     * assemble the inputParams to construct the response.
     * @return the json string wrapper which contains two keys: the encoded transaction byte array
     * in Base64 format, and the rawTransaction related params for future encoding.
     */
    @RequestMapping(value = DidServiceEndpoint.ENCODE_TRANSACTION, method = RequestMethod.POST)
    public HttpResponseData<Object> encodeTransaction(
        @RequestBody String encodeTransactionJsonArgs) {
        return transactionService.encodeTransaction(encodeTransactionJsonArgs);
    }

    /**
     * Send a signed Transaction to Chain.
     *
     * @param sendTransactionJsonArgs the json format args. It should contain three keys: the same
     * inputParams as in the createEncodeTransaction case, the signedMessage based on previous
     * encodedTransaction, and the functionName as to decide the SDK method endpoint.
     * @return the json string from SDK response.
     */
    @RequestMapping(value = DidServiceEndpoint.SEND_TRANSACTION, method = RequestMethod.POST)
    public HttpResponseData<Object> sendTransaction(
        @RequestBody String sendTransactionJsonArgs) {
        return transactionService.sendTransaction(sendTransactionJsonArgs);
    }

    /**
     * Invoke an SDK function.
     *
     * @param invokeFunctionJsonArgs the json format args. It should contain three keys: the same
     * inputParams as in the createEncodeTransaction case, the signedMessage based on previous
     * encodedTransaction, and the functionName as to decide the SDK method endpoint.
     * @return the json string from SDK response.
     */
    @RequestMapping(value = DidServiceEndpoint.INVOKE_FUNCTION, method = RequestMethod.POST)
    public HttpResponseData<Object> invokeFunction(
        @RequestBody String invokeFunctionJsonArgs) {
        return transactionService.invokeFunction(invokeFunctionJsonArgs);
    }

    /**
     * Get an invoke info.
     *
     * @return invoke info list.
     */
    @RequestMapping(value = DidServiceEndpoint.INVOKE_FUNCTION, method = RequestMethod.GET)
    public HttpResponseData<Object> invokeFunctionService(@RequestParam("args") String args) {
        return transactionService.invokeFunction(args);
    }

    /**
     * Get a verification result.
     *i
     * @return success or error html.
     */
    @RequestMapping(value = DidServiceEndpoint.VERIFY_FUNCTION, method = RequestMethod.GET)
    public ModelAndView verifyFunctionService(@RequestParam("args") String args) {
        HttpResponseData<Object> responseData = transactionService.invokeFunction(args);
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg", responseData.getErrorMessage());
        if(responseData.getErrorCode() == 0){
            mv.setViewName("success");
        } else {
            mv.setViewName("error");
        }
        return mv;
    }

}
