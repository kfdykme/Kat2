package xyz.kfdykme.kat.service.presenter;

import xyz.kfdykme.kat.basic.IPresenter;
import xyz.kfdykme.kat.service.model.IServiceModel;
import xyz.kfdykme.kat.service.model.ServiceModel;
import xyz.kfdykme.kat.service.view.IServiceView;
import xyz.kfdykme.kat.service.view.ServiceView;

/**
 * Project Name: kat
 * Class Description:
 * Created by kf on 2017/9/4 10:38.
 * Last Edit on 2017/9/4 10:38
 * 修改备注：
 */

public interface IServicePresenter extends IPresenter<ServiceModel,ServiceView>  {
    void seeNoteAfWrite();
}
