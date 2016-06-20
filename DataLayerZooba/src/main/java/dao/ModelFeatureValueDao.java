/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import abstractDao.AbstractDao;
import abstractDao.HibernateFactory;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import pojo.CarFeatures;
import pojo.ModelFeaturesValues;
import pojo.VehicleModel;

/**
 *
 * @author yoka
 */
public class ModelFeatureValueDao extends AbstractDao<ModelFeaturesValues> {

    Session session;

    public ModelFeatureValueDao(Session s) {
        super(ModelFeaturesValues.class, s);
        session = s;
    }

    @Override
    public void create(ModelFeaturesValues t) {
        if (t.getValue() != null) {
            ModelFeaturesValues modelFeaturesValues = getUniqueModelFeatureValueByValue(t.getValue());
            if (modelFeaturesValues == null) {
                super.create(t); //To change body of generated methods, choose Tools | Templates.
            }
        }
    }
  
    public ModelFeaturesValues find(int id) {
        return super.find(ModelFeaturesValues.class, id); //To change body of generated methods, choose Tools | Templates.
    }

    public List<ModelFeaturesValues> getMFValuesByCarFeature(String carFeatureName) {
        Criteria crt = session.createCriteria(ModelFeaturesValues.class, "modelf").
                createAlias("modelf.carFeatures", "carFeatures")
                .add(Restrictions.like("carFeatures.name", "%" + carFeatureName + "%"));
        List<ModelFeaturesValues> lst = crt.list();

        return lst;
    }

    public List<ModelFeaturesValues> getMFValuesByByVehicleModel(VehicleModel vModel) {
        Criteria crt = session.createCriteria(ModelFeaturesValues.class, "modelf").
                createAlias("modelf.vehicleModels", "vModel")
                .add(Restrictions.eq("vModel.model", vModel));
        List<ModelFeaturesValues> lst = crt.list();
        for (ModelFeaturesValues m : lst) {
            Hibernate.initialize(m.getCarFeatures().getName());
            Hibernate.initialize(m.getValue());
        }
        return lst;
    }

    public List<ModelFeaturesValues> getMFValuesByByCarFeatureAndVehicleModel(VehicleModel vmodel, String carFeatureName) {
        Criteria crt = session.createCriteria(ModelFeaturesValues.class, "modelf").
                createAlias("modelf.vehicleModels", "vModel")
                .add(Restrictions.eq("vModel.model", vmodel))
                .createAlias("modelf.carFeatures", "carFeatures")
                .add(Restrictions.like("carFeatures.name", "%" + carFeatureName + "%"));;
        List<ModelFeaturesValues> lst = crt.list();

        return lst;
    }

    public List<ModelFeaturesValues> getByCarFeatures(CarFeatures carFeatures) {

        List<ModelFeaturesValues> list = session.createQuery("SELECT m FROM ModelFeaturesValues m WHERE m.carFeatures.id = :id").setInteger("id", carFeatures.getId()).list();

        return list;
    }

    public List<ModelFeaturesValues> getByVehicleModel(VehicleModel vehicleModel) {

        List<ModelFeaturesValues> list = session.createQuery("SELECT m FROM VehicleModel v join v.modelFeaturesValueses m WHERE v.id = :id").setInteger("id", vehicleModel.getId()).list();

        return list;
    }
//     @Override
//    public void saveOrUpdate(ModelFeaturesValues m) {
//        super.saveOrUpdate(m); //To change body of generated methods, choose Tools | Templates.
//    } 

    private ModelFeaturesValues getUniqueModelFeatureValueByValue(String value) {

        Criteria crt = session.createCriteria(ModelFeaturesValues.class).add(Restrictions.eq("value", value));
        List<ModelFeaturesValues> list = (List<ModelFeaturesValues>) crt.list();
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
