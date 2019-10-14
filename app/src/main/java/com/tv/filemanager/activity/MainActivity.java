package com.tv.filemanager.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.owen.tab.TabItem;
import com.owen.tab.TvTabLayout;
import com.tv.filemanager.AppHelper;
import com.tv.filemanager.R;
import com.tv.filemanager.bean.CFile;
import com.tv.filemanager.blls.MainMenu;
import com.tv.filemanager.fragment.AbsCommonFragment;
import com.tv.filemanager.fragment.AllFileFragment;
import com.tv.filemanager.fragment.DiskFragment;
import com.tv.filemanager.fragment.InstallFragment;
import com.tv.filemanager.fragment.MusicFragment;
import com.tv.filemanager.fragment.OfficeFragment;
import com.tv.filemanager.fragment.PictureFragment;
import com.tv.filemanager.fragment.VideoFragment;
import com.tv.filemanager.utils.DialogUtil;
import com.tv.filemanager.utils.ToastUtil;
import com.tv.filemanager.widget.FolderWindowOp;
import com.tv.filemanager.widget.MenuWindowOp;
import com.tv.filemanager.widget.SortWindowOp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * 功能描述：主界面
 * 开发状况：正在开发中
 */
public class MainActivity extends AbsBaseActivity
        implements View.OnFocusChangeListener,TvTabLayout.OnTabSelectedListener {

    //响应按键菜单
    private MenuWindowOp mMenuWindowOp;
    //保存退出间隔时间
    private long mExitTime = 0;
    //排序入口
    private Button mSortBtn;
    //保存一级菜单
    private MainMenu mMainMenu;
    //路径和排序视图
    //在进入多存储盘时隐藏该视图
    private View mPathBarRl;
    //暂时当前进入的文件路径
    private TextView mPathTv;
    //磁盘按钮
    private TabItem mDiskBtn;
    //视频入口
    private TabItem mVideoBtn;
    //音乐入口
    private TabItem mMusicBtn;
    //清理入口
    private TextView mClearBtn;
    //设置入口
    private Button mSettingBtn;
    //所有文件入口
    private TabItem mAllFileBtn;
    //图片入口
    private TabItem mPictureBtn;
    //办公类入口
    private TabItem mOfficeBtn;
    //安装入口
    private TextView mInstallBtn;
    //卸载入口
    private TextView mUninstallBtn;
    //排序选择窗口
    private SortWindowOp mWindowOp;
    //办公类文件
    private OfficeFragment mOfficeFragment;
    //视屏碎片
    private VideoFragment mVideoFragment;
    //音乐文件碎片
    private MusicFragment mMusicFragment;
    //图片碎片
    private PictureFragment mPictureFragment;
    //安装文件碎片
    private InstallFragment mInstallFragment;
    //磁盘碎片
    private DiskFragment mDiskFragment;
    //所有的文件
    private AllFileFragment mAllFileFragment;

    private TvTabLayout tvTabLayout;

    @Override
    public void onCreateView() {
        EventBus.getDefault().register(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        setContentView(R.layout.activity_main_tab);
        tvTabLayout = (TvTabLayout) findViewById(R.id.tv_tablayout);
        mDiskBtn = (TabItem) tvTabLayout.findViewById(R.id.tv_disk_tab);
        mAllFileBtn = (TabItem) findViewById(R.id.btn_all_tab);
//        Log.d("wanglf", "onCreateView: " + mDiskBtn + mAllFileBtn);
        mVideoBtn = (TabItem) findViewById(R.id.btn_video_tab);
        mMusicBtn = (TabItem) findViewById(R.id.btn_music_tab);
        mPictureBtn = (TabItem) findViewById(R.id.btn_picture_tab);
        mOfficeBtn = (TabItem) findViewById(R.id.btn_office_tab);
//        mClearBtn = (TextView) findViewById(R.id.btn_clear);
//        mInstallBtn = (TextView) findViewById(R.id.btn_install);
//        mUninstallBtn = (TextView) findViewById(R.id.btn_uninstall);
//        mSettingBtn = (Button) findViewById(R.id.btn_setting);

        mPathTv = (TextView) findViewById(R.id.tv_path);
        mSortBtn = (Button) findViewById(R.id.btn_sort);
        mPathBarRl = findViewById(R.id.rl_path_bar);
    }

    @Override
    public void onInitObjects() {
        mMenuWindowOp = new MenuWindowOp(this);
        mWindowOp = new SortWindowOp(this);
        mMainMenu = new MainMenu();
        mDiskFragment = new DiskFragment();
        mInstallFragment = new InstallFragment();
        mMusicFragment = new MusicFragment();
        mPictureFragment = new PictureFragment();
        mVideoFragment = new VideoFragment();
        mOfficeFragment = new OfficeFragment();
        mAllFileFragment = new AllFileFragment();
    }

    @Override
    public void onSetListeners() {
//        mDiskBtn.setOnFocusChangeListener(this);
//        mAllFileBtn.setOnFocusChangeListener(this);
//        mVideoBtn.setOnFocusChangeListener(this);
//        mMusicBtn.setOnFocusChangeListener(this);
//        mPictureBtn.setOnFocusChangeListener(this);
//        mInstallBtn.setOnFocusChangeListener(this);
//        mOfficeBtn.setOnFocusChangeListener(this);

//        mDiskBtn.setOnClickListener(mListener);
//        mAllFileBtn.setOnClickListener(mListener);
//        mVideoBtn.setOnClickListener(mListener);
//        mMusicBtn.setOnClickListener(mListener);
//        mPictureBtn.setOnClickListener(mListener);
//        mOfficeBtn.setOnClickListener(mListener);
//        mInstallBtn.setOnClickListener(mListener);
//        mClearBtn.setOnClickListener(mListener);
//        mUninstallBtn.setOnClickListener(mListener);
//        mSettingBtn.setOnClickListener(mListener);
        mSortBtn.setOnClickListener(mListener);
        mMenuWindowOp.setMenuSelectedCallback(mMenuSelectCallback);
        tvTabLayout.setOnTabSelectedListener(this);
    }

    @Override
    public void onInitData(Bundle savedInstanceState) {
        ActivityCompat.requestPermissions(this,getPermissions(),REQUEST_CODE);
        Log.d("wanglf ", "onInitData: " + (mDiskBtn == null));
//        mMainMenu.putMenuView(mDiskBtn, mDiskFragment);
//        mMainMenu.putMenuView(mAllFileBtn, mAllFileFragment);
//        mMainMenu.putMenuView(mVideoBtn,mVideoFragment);
//        mMainMenu.putMenuView(mMusicBtn,mMusicFragment);
//        mMainMenu.putMenuView(mOfficeBtn,mOfficeFragment);
//        mMainMenu.putMenuView(mPictureBtn,mPictureFragment);
//        mMainMenu.putMenuView(mInstallBtn,mInstallFragment);

        //获取菜单名
        final String menuName = getIntent().getStringExtra("menuName");
        Log.d("wlf", "onInitData: " + menuName);
        if(!TextUtils.isEmpty(menuName)) {
            mMainMenu.setMenuChange(menuName, new MainMenu.IChangeCallback() {
                @Override
                public void onChange(MainMenu.Menu menu) {
                    menu.getMenuView().requestFocus();
                    menu.getMenuView().requestFocusFromTouch();
                    replaceFragment(menu.getId(),menu.getFragment());
                }
            });
        } else {
            replaceFragment(R.id.tv_disk,mDiskFragment);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void usbStatsChange(CFile file) {
        final AbsCommonFragment fragment = getFragment();
        Log.d("wanglf", "usbStatsChange : ...." + file.getPath() + " " + fragment);
        if (file.getPath() != null) {
            if (!(fragment instanceof DiskFragment)) {
                mDiskBtn.requestFocus();
                mDiskBtn.requestFocusFromTouch();
                replaceFragment(R.id.tv_disk, mDiskFragment);
            }
        }
        mDiskFragment.updateFile(file.getPath());
    }

    /**
     * 监听器
     */
    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final int menuId = v.getId();
            switch (menuId) {
                /*case R.id.tv_disk:
                    replaceFragment(menuId, mDiskFragment);
                    break;
                case R.id.btn_all:
                    replaceFragment(menuId, mAllFileFragment);
                    break;
                case R.id.btn_video:
                    replaceFragment(menuId, mVideoFragment);
                    break;
                case R.id.btn_music:
                    replaceFragment(menuId, mMusicFragment);
                    break;
                case R.id.btn_picture:
                    replaceFragment(menuId, mPictureFragment);
                    break;
                case R.id.btn_office:
                    replaceFragment(menuId, mOfficeFragment);
                    break;
                case R.id.btn_install:
                    replaceFragment(menuId, mInstallFragment);
                    break;
                case R.id.btn_clear:
                    startActivityForResult(new Intent(v.getContext(),CleanActivity.class),2);
                    break;
                case R.id.btn_uninstall:
                    startActivityForResult(new Intent(v.getContext(),UninstallActivity.class),3);
                    break;
                case R.id.btn_setting:
                    startActivityForResult(new Intent(v.getContext(),SettingActivity.class),4);
                    break;*/
                case R.id.btn_sort:
                    if(mMainMenu.clearMuselectModel()) {
                        mMenuWindowOp.resetMuSelectBtnText();
                    } else {
                        mWindowOp.windowOption(v);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    //用于监听
    private MenuWindowOp.IMenuSelectCallback mMenuSelectCallback = new MenuWindowOp.IMenuSelectCallback() {
        @Override
        public void onSelected(View view, CFile selectedFilePath) {
            final AbsCommonFragment fragment = getFragment();
            switch (view.getId()) {
                case R.id.tv_menu_create:
                    DialogUtil.createNewFolderDialog(fragment);
                    break;
                case R.id.tv_menu_delete:
                    if(selectedFilePath != null) {
                        if(!fragment.isMuselectModel()) {
                            DialogUtil.deleteFileDialog(fragment,selectedFilePath);
                        } else {
                            DialogUtil.deleteFileDialog(fragment,fragment.getMuselectedData());
                        }
                    } else {
                        ToastUtil.toast(view.getContext(),getString(R.string.not_select_file));
                    }
                    break;
                case R.id.tv_menu_copy:
                    final FolderWindowOp windowOp = new FolderWindowOp(view.getContext());
                    if(!fragment.isMuselectModel()) {
                        windowOp.setCopyFile(selectedFilePath);
                    } else {
                        windowOp.setCopyFiles(fragment.getMuselectedData());
                    }
                    windowOp.windowOption(tvTabLayout);
                    break;
                case R.id.tv_menu_select_mode:
                    final TextView muselected = (TextView)view;
                    if(TextUtils.equals(getStr(R.string.multiple_choice), muselected.getText().toString().trim())) {
                        fragment.setAdapterMuselectModel(true);
                        mMenuWindowOp.muselectModel();
                    } else {
                        fragment.setAdapterMuselectModel(false);
                        mMenuWindowOp.resetMuSelectBtnText();
                        if(fragment instanceof DiskFragment) {
                            mMenuWindowOp.hideMenuViewById(R.id.tv_menu_select_mode, View.VISIBLE);
                        }
                    }
                    break;
                case R.id.tv_menu_rename:
                    if(selectedFilePath != null) {
                        DialogUtil.renameFileDialog(fragment,selectedFilePath.getPath());
                    } else {
                        ToastUtil.toast(view.getContext(),getString(R.string.not_select_file));
                    }
                    break;
                case R.id.tv_menu_file_info:
                    if(selectedFilePath != null) {
                        DialogUtil.showFileInfoDialog(view.getContext(),selectedFilePath.getPath());
                    } else {
                        ToastUtil.toast(view.getContext(),getString(R.string.not_select_file));
                    }
                    break;
                case R.id.tv_menu_send :
//                    FileUtil.sendFile(this,);
                    break;
                default:
                    break;
            }
            mMainMenu.currentMenuRequestFocus();
        }
    };

    private AbsCommonFragment mFrament;
    private AbsCommonFragment getFragment() {
        return mFrament;
    }
    /**
     * 替换碎片
     * @param fragment 目标碎片
     */
    private void replaceFragment(int menuId,AbsCommonFragment fragment) {
        mFrament = fragment;
        if(mMainMenu.clearMuselectModel()) {
            mMenuWindowOp.resetMuSelectBtnText();
            return;
        }
        if(!mMainMenu.isCurrentMenuId(menuId)) {
            Log.d("wanglf", "replaceFragment: 111" + menuId);
            if(menuId > 0) {
                mMainMenu.setMenuChange(menuId);
            }
            getFragmentHandler().replaceFragment(R.id.fl_container, fragment);
        } else{
            Log.d("wanglf", "replaceFragment: ....");
            getFragmentHandler().replaceFragment(R.id.fl_container, fragment);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 2:
            case 3:
            case 4:
                mMainMenu.currentMenuRequestFocus();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        final AbsCommonFragment fragment = getFragment();
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if(fragment.isMuselectModel()) {
                fragment.setAdapterMuselectModel(false);
                mMenuWindowOp.resetMuSelectBtnText();
                return true;
            }
            if(fragment.onKeyDown()) {
                return true;
            }
            return isExitApp();
        } else if(keyCode == KeyEvent.KEYCODE_MENU) {
            mMenuWindowOp.setSelectedFile(mPathTv,null);
            if(fragment instanceof DiskFragment) {
                if(!((DiskFragment) fragment).isDiskData()) {
                    if(!fragment.isMuselectModel()) {
                        mMenuWindowOp.hideMenuViewById(R.id.tv_menu_create,View.VISIBLE);
                    } else {
                        mMenuWindowOp.hideMenuViewById(R.id.tv_menu_create,View.GONE);
                    }
                    mMenuWindowOp.windowOption();
                }
            } else {
                if(fragment instanceof AllFileFragment) {
                    if(fragment.isMuselectModel()) {
                        mMenuWindowOp.hideMenuViewById(R.id.tv_menu_create,View.GONE);
                    } else {
                        mMenuWindowOp.hideMenuViewById(R.id.tv_menu_create,View.VISIBLE);
                    }
                    mMenuWindowOp.windowOption();
                } else {
                    mMenuWindowOp.windowOption(R.id.tv_menu_create);
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }

    /**
     * 退出应用程序
     * @return 是否退出
     */
    private boolean isExitApp() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - mExitTime < 2000) {
            AppHelper.instance().exitApp();
        } else {
            mExitTime = currentTime;
            ToastUtil.toast(this,"再按一次退出");
        }
        return true;
    }

    /**
     * 设置当前被选中的文件路径
     * @param view 视图
     * @param path 路径
     */
    public void setCurrSelectedFilePath(View view,CFile path) {
        if(path != null) {
            mPathTv.setText(path.getPath());
            mMenuWindowOp.setSelectedFile(view,path);
        } else {
            mMenuWindowOp.setSelectedFile(view,null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public TextView getPathTv() {
        return mPathTv;
    }

    public View getPathBarRl() {
        return mPathBarRl;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus) {
            ((TextView)v).setTextColor(Color.YELLOW);
            mMainMenu.setMenuTextColor(v.getId(), Color.WHITE);
        } else {
            if(mMainMenu.isCurrentMenuId(v.getId())) {
                mMainMenu.getCurrentMenu().getMenuView().setTextColor(getResources().getColor(R.color.color5));
            } else {
                ((TextView)v).setTextColor(Color.WHITE);
            }
        }
    }

    /**
     * 获取主菜单
     * @return 菜单
     */
    public MainMenu getMainMenu() {
        return mMainMenu;
    }

    /**
     * 获取排序按钮
     * @return 排序按钮实例
     */
    public Button getSortBtn() {
        return mSortBtn;
    }

    /**
     * 获取字符串
     * @param resId 资源Id
     * @return 字符串
     */
    private String getStr(int resId) {
        return getResources().getString(resId);
    }

    @Override
    public void onTabSelected(TvTabLayout.Tab tab) {
        Log.d("wanglf", "onTabSelected: " +tab.getPosition());
        switch (tab.getPosition()) {
            case 0 :
                replaceFragment(tab.getPosition(),mDiskFragment);
                break;
            case 1 :
                Log.d("wanglf", "onTabSelected: .....");
                replaceFragment(tab.getPosition(),mAllFileFragment);
                break;
            case 2 :
                replaceFragment(tab.getPosition(),mVideoFragment);
                break;
            case 3 :
                replaceFragment(tab.getPosition(),mMusicFragment);
                break;
            case 4 :
                replaceFragment(tab.getPosition(),mPictureFragment);
                break;
            case 5 :
                replaceFragment(tab.getPosition(),mOfficeFragment);
                break;
        }
    }

    @Override
    public void onTabUnselected(TvTabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TvTabLayout.Tab tab) {

    }
}
