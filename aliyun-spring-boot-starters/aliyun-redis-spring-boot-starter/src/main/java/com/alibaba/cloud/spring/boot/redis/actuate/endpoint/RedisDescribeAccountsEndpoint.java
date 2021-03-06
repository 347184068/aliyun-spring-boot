/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.cloud.spring.boot.redis.actuate.endpoint;

import com.alibaba.cloud.spring.boot.context.env.AliCloudProperties;
import com.alibaba.cloud.spring.boot.redis.env.RedisProperties;
import com.aliyuncs.r_kvstore.model.v20150101.DescribeAccountsRequest;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;

/**
 * The {@link Endpoint} for Alibaba Cloud Redis's
 * <a href= "https://help.aliyun.com/document_detail/95802.html">DescribeAccounts</a>
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since 1.0.0
 */
@Endpoint(id = "redisDescribeAccounts")
public class RedisDescribeAccountsEndpoint extends AbstractRedisEndpoint {

	public RedisDescribeAccountsEndpoint(AliCloudProperties aliCloudProperties,
										 RedisProperties redisProperties) {
		super(aliCloudProperties, redisProperties);
	}

	@ReadOperation
	public Object describeAccountsWithDefaultRegionId() {
		return describeAccounts(getDefaultRegionID());
	}

	@ReadOperation
	public Object describeAccounts(@Selector String regionId) {
		return execute(() -> {
			DescribeAccountsRequest request = new DescribeAccountsRequest();
			request.setSysRegionId(regionId);
			request.setInstanceId(getInstanceId());
			return createIAcsClient(regionId).getAcsResponse(request);
		});
	}
}
