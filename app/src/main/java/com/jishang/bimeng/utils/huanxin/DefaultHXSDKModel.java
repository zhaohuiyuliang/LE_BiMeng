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

/**
 * UI Demo HX Model implementation
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * HuanXin default SDK Model implementation
 * @author easemob
 *
 */
public class DefaultHXSDKModel extends HXSDKModel{
    private static final String PREF_USERNAME1 = "username";
    private static final String PREF_PWD1 = "pwd";
    protected Context context = null;
    protected Map<Key,Object> valueCache = new HashMap<Key,Object>();
    
    public DefaultHXSDKModel(Context ctx){
        context = ctx;
    }
    
    @Override
    public void setSettingMsgNotification(boolean paramBoolean) {
        valueCache.put(Key.VibrateAndPlayToneOn, paramBoolean);
    }

    @Override
    public boolean getSettingMsgNotification() {
        Object val = valueCache.get(Key.VibrateAndPlayToneOn);

        if(val == null){
            valueCache.put(Key.VibrateAndPlayToneOn, val);
        }
        return (Boolean) (val != null?val:true);
    }

    @Override
    public void setSettingMsgSound(boolean paramBoolean) {
        valueCache.put(Key.PlayToneOn, paramBoolean);
    }

    @Override
    public boolean getSettingMsgSound() {
        Object val = valueCache.get(Key.PlayToneOn);

        if(val == null){
            valueCache.put(Key.PlayToneOn, val);
        }
       
        return (Boolean) (val != null?val:true);
    }

    @Override
    public void setSettingMsgVibrate(boolean paramBoolean) {
        valueCache.put(Key.VibrateOn, paramBoolean);
    }

    @Override
    public boolean getSettingMsgVibrate() {
        Object val = valueCache.get(Key.VibrateOn);

        if(val == null){
            valueCache.put(Key.VibrateOn, val);
        }
        return (Boolean) (val != null?val:true);
    }

    @Override
    public void setSettingMsgSpeaker(boolean paramBoolean) {
        valueCache.put(Key.SpakerOn, paramBoolean);
    }

    @Override
    public boolean getSettingMsgSpeaker() {
        Object val = valueCache.get(Key.SpakerOn);
        if(val == null){
            valueCache.put(Key.SpakerOn, val);
        }
        return (Boolean) (val != null?val:true);
    }

    @Override
    public boolean getUseHXRoster() {
        return false;
    }

    @Override
    public boolean saveHXId(String hxId) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.edit().putString("username", hxId).commit();
    }

    @Override
    public String getHXId() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("username", null);
    }

    @Override
    public boolean savePassword(String pwd) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.edit().putString("pwd", pwd).commit();    
    }

    @Override
    public String getPwd() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("pwd", null);
    }

    @Override
    public String getAppProcessName() {
        return null;
    }
    
    public void setDisabledGroups(List<String> groups){
        valueCache.put(Key.DisabledGroups, groups);
    }
    
    public List<String> getDisabledGroups(){
        Object val = valueCache.get(Key.DisabledGroups);
        return (List<String>) val;
    }
    
    public void setDisabledIds(List<String> ids){
        valueCache.put(Key.DisabledIds, ids);
    }
    
    public List<String> getDisabledIds(){
        Object val = valueCache.get(Key.DisabledIds);
       
        return (List<String>) val;
    }
   
    public void allowChatroomOwnerLeave(boolean value){
    }
    
    public boolean isChatroomOwnerLeaveAllowed(){
        return true;
    }
    
    public void setGroupsSynced(boolean synced){
    }
    
    public boolean isGroupsSynced(){
    	  return true;
    }
    
    public void setContactSynced(boolean synced){
    }
    
    public boolean isContactSynced(){
    	  return true;
    }
    
    public void setBlacklistSynced(boolean synced){
    }
    
    public boolean isBacklistSynced(){
    	  return true;
    }
    
    enum Key{
        VibrateAndPlayToneOn,
        VibrateOn,
        PlayToneOn,
        SpakerOn,
        DisabledGroups,
        DisabledIds
    }
}
