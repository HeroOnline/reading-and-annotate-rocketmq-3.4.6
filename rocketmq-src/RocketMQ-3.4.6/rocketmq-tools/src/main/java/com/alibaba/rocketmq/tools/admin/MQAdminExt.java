/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.rocketmq.tools.admin;

import com.alibaba.rocketmq.client.MQAdmin;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.TopicConfig;
import com.alibaba.rocketmq.common.admin.ConsumeStats;
import com.alibaba.rocketmq.common.admin.RollbackStats;
import com.alibaba.rocketmq.common.admin.TopicStatsTable;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.alibaba.rocketmq.common.protocol.body.*;
import com.alibaba.rocketmq.common.protocol.route.TopicRouteData;
import com.alibaba.rocketmq.common.subscription.SubscriptionGroupConfig;
import com.alibaba.rocketmq.remoting.exception.*;
import com.alibaba.rocketmq.tools.admin.api.MessageTrack;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;


public interface MQAdminExt extends MQAdmin {
    void start() throws MQClientException;

    void shutdown();

    void updateBrokerConfig(final String brokerAddr, final Properties properties) throws RemotingConnectException,
            RemotingSendRequestException, RemotingTimeoutException, UnsupportedEncodingException, InterruptedException, MQBrokerException;

    void createAndUpdateTopicConfig(final String addr, final TopicConfig config) throws RemotingException, MQBrokerException,
            InterruptedException, MQClientException;


    void createAndUpdateSubscriptionGroupConfig(final String addr, final SubscriptionGroupConfig config) throws RemotingException,
            MQBrokerException, InterruptedException, MQClientException;


    SubscriptionGroupConfig examineSubscriptionGroupConfig(final String addr, final String group);


    TopicConfig examineTopicConfig(final String addr, final String topic);


    TopicStatsTable examineTopicStats(final String topic) throws RemotingException, MQClientException, InterruptedException,
            MQBrokerException;


    TopicList fetchAllTopicList() throws RemotingException, MQClientException, InterruptedException;


    KVTable fetchBrokerRuntimeStats(final String brokerAddr) throws RemotingConnectException, RemotingSendRequestException,
            RemotingTimeoutException, InterruptedException, MQBrokerException;


    ConsumeStats examineConsumeStats(final String consumerGroup) throws RemotingException, MQClientException, InterruptedException,
            MQBrokerException;


    ConsumeStats examineConsumeStats(final String consumerGroup, final String topic) throws RemotingException, MQClientException,
            InterruptedException, MQBrokerException;

    ClusterInfo examineBrokerClusterInfo() throws InterruptedException, MQBrokerException, RemotingTimeoutException,
            RemotingSendRequestException, RemotingConnectException;


    TopicRouteData examineTopicRouteInfo(final String topic) throws RemotingException, MQClientException, InterruptedException;


    ConsumerConnection examineConsumerConnectionInfo(final String consumerGroup) throws RemotingConnectException,
            RemotingSendRequestException, RemotingTimeoutException, InterruptedException, MQBrokerException, RemotingException,
            MQClientException;


    ProducerConnection examineProducerConnectionInfo(final String producerGroup, final String topic) throws RemotingException,
            MQClientException, InterruptedException, MQBrokerException;


    List<String> getNameServerAddressList();

    int wipeWritePermOfBroker(final String namesrvAddr, String brokerName) throws RemotingCommandException,
            RemotingConnectException, RemotingSendRequestException, RemotingTimeoutException, InterruptedException, MQClientException;

    void putKVConfig(final String namespace, final String key, final String value);

    String getKVConfig(final String namespace, final String key) throws RemotingException, MQClientException, InterruptedException;


    KVTable getKVListByNamespace(final String namespace) throws RemotingException, MQClientException, InterruptedException;


    void deleteTopicInBroker(final Set<String> addrs, final String topic) throws RemotingException, MQBrokerException,
            InterruptedException, MQClientException;


    void deleteTopicInNameServer(final Set<String> addrs, final String topic) throws RemotingException, MQBrokerException,
            InterruptedException, MQClientException;


    void deleteSubscriptionGroup(final String addr, String groupName) throws RemotingException, MQBrokerException,
            InterruptedException, MQClientException;


    void createAndUpdateKvConfig(String namespace, String key, String value) throws RemotingException, MQBrokerException,
            InterruptedException, MQClientException;


    void deleteKvConfig(String namespace, String key) throws RemotingException, MQBrokerException, InterruptedException,
            MQClientException;


    List<RollbackStats> resetOffsetByTimestampOld(String consumerGroup, String topic, long timestamp, boolean force)
            throws RemotingException, MQBrokerException, InterruptedException, MQClientException;


    Map<MessageQueue, Long> resetOffsetByTimestamp(String topic, String group, long timestamp, boolean isForce)
            throws RemotingException, MQBrokerException, InterruptedException, MQClientException;


    void resetOffsetNew(String consumerGroup, String topic, long timestamp) throws RemotingException, MQBrokerException,
            InterruptedException, MQClientException;


    Map<String, Map<MessageQueue, Long>> getConsumeStatus(String topic, String group, String clientAddr) throws RemotingException,
            MQBrokerException, InterruptedException, MQClientException;


    void createOrUpdateOrderConf(String key, String value, boolean isCluster) throws RemotingException, MQBrokerException,
            InterruptedException, MQClientException;


    GroupList queryTopicConsumeByWho(final String topic) throws RemotingConnectException, RemotingSendRequestException,
            RemotingTimeoutException, InterruptedException, MQBrokerException, RemotingException, MQClientException;


    List<QueueTimeSpan> queryConsumeTimeSpan(final String topic, final String group) throws InterruptedException, MQBrokerException,
            RemotingException, MQClientException;

    boolean cleanExpiredConsumerQueue(String cluster) throws RemotingConnectException, RemotingSendRequestException,
            RemotingTimeoutException, MQClientException, InterruptedException;

    boolean cleanExpiredConsumerQueueByAddr(String addr) throws RemotingConnectException, RemotingSendRequestException,
            RemotingTimeoutException, MQClientException, InterruptedException;

    boolean cleanUnusedTopic(String cluster) throws RemotingConnectException, RemotingSendRequestException,
            RemotingTimeoutException, MQClientException, InterruptedException;

    boolean cleanUnusedTopicByAddr(String addr) throws RemotingConnectException, RemotingSendRequestException,
            RemotingTimeoutException, MQClientException, InterruptedException;

    ConsumerRunningInfo getConsumerRunningInfo(final String consumerGroup, final String clientId, final boolean jstack)
            throws RemotingException, MQClientException, InterruptedException;

    ConsumeMessageDirectlyResult consumeMessageDirectly(String consumerGroup, //
                                                        String clientId, //
                                                        String msgId) throws RemotingException, MQClientException, InterruptedException, MQBrokerException;

    List<MessageTrack> messageTrackDetail(MessageExt msg) throws RemotingException, MQClientException, InterruptedException,
            MQBrokerException;

    void cloneGroupOffset(String srcGroup, String destGroup, String topic, boolean isOffline) throws RemotingException,
            MQClientException, InterruptedException, MQBrokerException;

    BrokerStatsData ViewBrokerStatsData(final String brokerAddr, final String statsName, final String statsKey)
            throws RemotingConnectException, RemotingSendRequestException, RemotingTimeoutException, MQClientException,
            InterruptedException;

    Set<String> getClusterList(final String topic) throws RemotingConnectException, RemotingSendRequestException,
            RemotingTimeoutException, MQClientException, InterruptedException;

    ConsumeStatsList fetchConsumeStatsInBroker(final String brokerAddr, boolean isOrder, long timeoutMillis) throws RemotingConnectException, RemotingSendRequestException,
            RemotingTimeoutException, MQClientException, InterruptedException;

    Set<String> getTopicClusterList(final String topic) throws InterruptedException, MQBrokerException, MQClientException, RemotingException;

}
