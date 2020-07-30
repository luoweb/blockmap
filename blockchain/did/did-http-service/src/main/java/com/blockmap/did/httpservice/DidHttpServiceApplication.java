/*
 *       Copyright© (2020) ICBC Co., Ltd.
 *
 *       This file is part of did-http-com.icbc.did.core.service.
 *
 *       did-http-com.icbc.did.core.service is free software: you can redistribute it and/or modify
 *       it under the terms of the GNU Lesser General Public License as published by
 *       the Free Software Foundation, either version 3 of the License, or
 *       (at your option) any later version.
 *
 *       did-http-com.icbc.did.core.service is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU Lesser General Public License for more details.
 *
 *       You should have received a copy of the GNU Lesser General Public License
 *       along with did-http-com.icbc.did.core.service.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.icbc.did.httpservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

/**
 * @Author: xuzj01@sdc.icbc.com.cn
 * @Description:
 * @File: DidHttpServiceApplication
 * @Version: 1.0.0
 * @Date: 2019/12/15 18:45
 */

@SpringBootApplication(
        exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class}
)
public class DidHttpServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DidHttpServiceApplication.class, args);
    }

}
