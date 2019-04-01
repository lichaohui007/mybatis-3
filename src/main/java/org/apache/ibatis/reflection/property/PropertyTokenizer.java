/**
 *    Copyright 2009-2017 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.reflection.property;

import java.util.Iterator;

/**
 * @author Clinton Begin
 */
public class PropertyTokenizer implements Iterator<PropertyTokenizer> {
  // 当前字符串
  private String name;
  // 索引的name  如果存在会被更改
  private final String indexedName;
  // 编号
  /**
   * 如果数组  name[0]  则index =0
   * 对于map[key]  则index=key
   */
  private String index;
  //剩余字符串
  private final String children;

  public PropertyTokenizer(String fullname) {
    // 初始化name children  字符串 使用 。 作为分隔
    int delim = fullname.indexOf('.');

    if (delim > -1) {
      name = fullname.substring(0, delim);
      children = fullname.substring(delim + 1);
    } else {
      name = fullname;
      children = null;
    }
    //记录当前name
    indexedName = name;
    //找出 【 的位置
    delim = name.indexOf('[');
    if (delim > -1) {
      //截取 【】  之间的数字
      index = name.substring(delim + 1, name.length() - 1);
      // 截取 【 之前的name
      name = name.substring(0, delim);
    }
  }

  public String getName() {
    return name;
  }

  public String getIndex() {
    return index;
  }

  public String getIndexedName() {
    return indexedName;
  }

  public String getChildren() {
    return children;
  }

  @Override
  public boolean hasNext() {
    return children != null;
  }

  //截取下一个元素
  @Override
  public PropertyTokenizer next() {
    return new PropertyTokenizer(children);
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException("Remove is not supported, as it has no meaning in the context of properties.");
  }
}
