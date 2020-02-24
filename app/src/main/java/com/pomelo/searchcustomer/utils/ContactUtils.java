package com.pomelo.searchcustomer.utils;

import android.Manifest;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;
import com.qw.soul.permission.bean.Permissions;
import com.qw.soul.permission.callbcak.CheckRequestPermissionListener;
import com.qw.soul.permission.callbcak.CheckRequestPermissionsListener;

import java.util.List;

/**
 * Created by wanghaoxiang on 2020-01-18.
 */

public class ContactUtils {
    public static void requestPermisson(Context mContext) {
        //请求读取电话权限
        SoulPermission.getInstance().checkAndRequestPermissions(Permissions.build(Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS), new CheckRequestPermissionsListener() {

            @Override
            public void onAllPermissionOk(Permission[] allPermissions) {

            }

            @Override
            public void onPermissionDenied(Permission[] refusedPermissions) {
                for (Permission permission : refusedPermissions) {
                    if (permission.shouldRationale) {
                        new AlertDialog.Builder(mContext)
                                .setTitle("提示")
                                .setMessage("如果你拒绝了权限，你将无法使用该软件，请点击授予权限")
                                .setPositiveButton("授予", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                }).create().show();
                    } else {
                        String permissionDesc = permission.getPermissionNameDesc();
                        new AlertDialog.Builder(mContext)
                                .setTitle("提示")
                                .setMessage(permissionDesc + "异常，请前往设置－>权限管理，打开" + permissionDesc + "。")
                                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //去设置页
                                        SoulPermission.getInstance().goPermissionSettings();
                                    }
                                }).create().show();
                    }
                }
            }
        });
    }

    public static void saveExistContact(Context context, String name, String phone) {
        Intent intent = new Intent(Intent.ACTION_INSERT_OR_EDIT);
        intent.setType("vnd.android.cursor.item/person");
        intent.setType("vnd.android.cursor.item/contact");
        intent.setType("vnd.android.cursor.item/raw_contact");
        //    intent.putExtra(android.provider.ContactsContract.Intents.Insert.NAME, name);
        intent.putExtra(android.provider.ContactsContract.Intents.Insert.PHONE, phone);
        intent.putExtra(android.provider.ContactsContract.Intents.Insert.PHONE_TYPE, 3);
        context.startActivity(intent);
    }

    public static void addNewContact(Context context, String name, String phoneNumber) {
        Uri insertUri = android.provider.ContactsContract.Contacts.CONTENT_URI;
        Intent intent = new Intent(Intent.ACTION_INSERT, insertUri);
        intent.putExtra(android.provider.ContactsContract.Intents.Insert.NAME, name);//名字显示在名字框
        intent.putExtra(android.provider.ContactsContract.Intents.Insert.PHONE, phoneNumber);//号码显示在号码框
//        intent.putExtra(ContactsContract.Intents.Insert.POSTAL,"");//地址显示在地址框
        context.startActivity(intent);
    }
}
