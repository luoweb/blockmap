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


package com.icbc.did.httpservice.service;

import com.icbc.did.httpservice.protocol.request.InputArg;
import com.icbc.did.httpservice.protocol.response.HttpResponseData;
import org.springframework.stereotype.Service;

/**
 * @Author: xuzj01@sdc.icbc.com.cn
 * @Description:
 * @File: InvokerQrCodeService
 * @Version: 1.0.0
 * @Date: 2019/12/16 20:01
 */

@Service
public interface InvokerQrCodeService {


    HttpResponseData<Object> createQrCodeInvoke(InputArg createQrCodeFuncArgs);


    HttpResponseData<Object> activeQrCodeInvoke(InputArg activeQrCodeFuncArgs);


}
