/**
 * Copyright (C) 2015-2019 Expedia, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hotels.beeju.extensions;

import java.util.Map;

import org.junit.jupiter.api.extension.ExtensionContext;

import com.hotels.beeju.core.ThriftHiveMetaStoreCore;

/**
 * A JUnit Extension that creates a Hive Metastore Thrift service backed by a Hive Metastore using an in-memory
 * database.
 * <p>
 * A fresh database instance will be created for each test method.
 * </p>
 */
public class ThriftHiveMetaStoreJUnitExtension extends HiveMetaStoreJUnitExtension {

  private final ThriftHiveMetaStoreCore thriftHiveMetaStoreCore;

  /**
   * Create a Thrift Hive Metastore service with a pre-created database "test_database".
   */
  public ThriftHiveMetaStoreJUnitExtension() {
    this("test_database");
  }

  /**
   * Create a Thrift Hive Metastore service with a pre-created database using the provided name.
   *
   * @param databaseName Database name.
   */
  public ThriftHiveMetaStoreJUnitExtension(String databaseName) {
    this(databaseName, null);
  }

  /**
   * Create a Thrift Hive Metastore service with a pre-created database using the provided name and configuration.
   *
   * @param databaseName Database name.
   * @param configuration Hive configuration properties.
   */
  public ThriftHiveMetaStoreJUnitExtension(String databaseName, Map<String, String> configuration) {
    super(databaseName, configuration);
    thriftHiveMetaStoreCore = new ThriftHiveMetaStoreCore(core);
  }

  @Override
  public void beforeEach(ExtensionContext context) throws Exception{
    thriftHiveMetaStoreCore.initialise();
    super.beforeEach(context);
  }

  @Override
  public void afterEach(ExtensionContext context) throws Exception {
    thriftHiveMetaStoreCore.shutdown();
    super.afterEach(context);
  }

  /**
   * @return {@link com.hotels.beeju.core.ThriftHiveMetaStoreCore#getThriftConnectionUri()}.
   */
  public String getThriftConnectionUri() {
    return thriftHiveMetaStoreCore.getThriftConnectionUri();
  }

  /**
   * @return {@link com.hotels.beeju.core.ThriftHiveMetaStoreCore#getThriftPort()}
   */
  public int getThriftPort() {
    return thriftHiveMetaStoreCore.getThriftPort();
  }
}
