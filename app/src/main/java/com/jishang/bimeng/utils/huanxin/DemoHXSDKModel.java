/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jishang.bimeng.utils.huanxin;

import java.util.List;
import java.util.Map;

import android.content.Context;

import com.jishang.bimeng.activity.hxchat.User;

public class DemoHXSDKModel extends DefaultHXSDKModel{

    public DemoHXSDKModel(Context ctx) {
        super(ctx);
    }

    // demo will not use HuanXin roster
    public boolean getUseHXRoster() {
        return false;
    }
    
    // demo will switch on debug mode
    public boolean isDebugMode(){
        return true;
    }
    
    public boolean saveContactList(List<User> contactList) {
        return true;
    }

    public Map<String, User> getContactList() {
        return null;
    }

    public void closeDB() {
    }
    
    @Override
    public String getAppProcessName() {
        return context.getPackageName();
    }
}
