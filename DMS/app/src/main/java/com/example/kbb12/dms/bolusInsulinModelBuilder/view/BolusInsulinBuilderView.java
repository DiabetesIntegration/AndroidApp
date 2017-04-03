package com.example.kbb12.dms.bolusInsulinModelBuilder.view;

import android.app.FragmentManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kbb12.dms.R;
import com.example.kbb12.dms.bolusInsulinModelBuilder.controller.IBolusControllerFactory;
import com.example.kbb12.dms.bolusInsulinModelBuilder.model.BolusInsulinReadModel;
import com.example.kbb12.dms.baseScreen.controller.IErrorController;
import com.example.kbb12.dms.baseScreen.view.MasterView;
import com.example.kbb12.dms.startUp.ModelObserver;

/**
 * Created by kbb12 on 10/03/2017.
 */
public class BolusInsulinBuilderView extends MasterView implements ModelObserver {

    private LinearLayout insulinToCarbDetailHolder;
    private View icrDetails;
    private boolean knewICR;
    private LinearLayout insulinSensitivityFactorDetailsHolder;
    private View isfDetails;
    private boolean knewISF;
    private BolusInsulinReadModel model;
    private Context context;
    private IBolusControllerFactory factory;


    public BolusInsulinBuilderView(FragmentManager fragMan, IErrorController controller,
                                   LinearLayout insulinToCarbDetailHolder,
                                   LinearLayout insulinSensitivityFactorDetailsHolder,
                                   BolusInsulinReadModel model,Context context,
                                   IBolusControllerFactory factory) {
        super(fragMan, controller);
        this.insulinToCarbDetailHolder=insulinToCarbDetailHolder;
        this.insulinSensitivityFactorDetailsHolder = insulinSensitivityFactorDetailsHolder;
        this.model=model;
        this.context=context;
        this.factory=factory;
        setICRDetails();
        setISFDetails();
    }

    private void setICRDetails(){
        insulinToCarbDetailHolder.removeAllViews();
        if(model.knowsICR()){
            knewICR=true;
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout holder = new LinearLayout(context);
            LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            holder.setLayoutParams(layoutParams);
            icrDetails = inflater.inflate(R.layout.know_insulin_to_carb,holder,false);
            setBreakInsulin();
            setBreakCarbs();
            setLunInsulin();
            setLunCarbs();
            setDinInsulin();
            setDinCarbs();
            setNighInsulin();
            setNighCarbs();
            insulinToCarbDetailHolder.addView(icrDetails);
        }else{
            knewICR=false;
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout holder = new LinearLayout(context);
            LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            holder.setLayoutParams(layoutParams);
            icrDetails = inflater.inflate(R.layout.dont_know_insulin_to_carb,holder,false);
            setNumBolUnits();
            setHumalogNovolog();
            setICRResult();
            insulinToCarbDetailHolder.addView(icrDetails);
        }
    }

    private void setICRResult(){
        TextView icrResult = (TextView) icrDetails.findViewById(R.id.icrResult);
        if(model.getICR()==null){
            icrResult.setText("Enter your details to calculate your approximate insulin:carb ratio.");
        }else {
            icrResult.setText("This gives an approximate insulin to carb ratio of    1:" + 1/model.getICR());
        }
    }

    private void setHumalogNovolog(){
        CheckBox humalogNovolog =(CheckBox) icrDetails.findViewById(R.id.humalogNovolog);
        humalogNovolog.setChecked(model.isHumalogNovolog());
        humalogNovolog.setOnCheckedChangeListener(factory.getHumalogNovologListener());
    }

    private void setNumBolUnits(){
        EditText numBolUnits =(EditText) icrDetails.findViewById(R.id.numBolUnits);
        if(model.getNumBolUnitsPerDay()!=null){
            numBolUnits.setText(model.getNumBolUnitsPerDay().toString());
        }
        numBolUnits.addTextChangedListener(factory.getNumBolusUnitsListener());
    }

    private void setBreakInsulin(){
        EditText breakInsulin =(EditText) icrDetails.findViewById(R.id.breakfastInsulin);
        if(model.getBreakInsulin()!=null){
            breakInsulin.setText(model.getBreakInsulin().toString());
        }
        breakInsulin.addTextChangedListener(factory.getBreakInsulinListener());
    }

    private void setBreakCarbs(){
        EditText breakCarbs =(EditText) icrDetails.findViewById(R.id.breakfastCarbs);
        if(model.getBreakCarbs()!=null){
            breakCarbs.setText(model.getBreakCarbs().toString());
        }
        breakCarbs.addTextChangedListener(factory.getBreakCarbListener());
    }

    private void setLunInsulin(){
        EditText lunInsulin =(EditText) icrDetails.findViewById(R.id.lunchinsulin);
        if(model.getLunInsulin()!=null){
            lunInsulin.setText(model.getLunInsulin().toString());
        }
        lunInsulin.addTextChangedListener(factory.getLunchInsulinListener());
    }

    private void setLunCarbs(){
        EditText lunCarbs =(EditText) icrDetails.findViewById(R.id.lunchCarbs);
        if(model.getLunCarbs()!=null){
            lunCarbs.setText(model.getLunCarbs().toString());
        }
        lunCarbs.addTextChangedListener(factory.getLunchCarbListener());
    }

    private void setDinInsulin(){
        EditText dinInsulin =(EditText) icrDetails.findViewById(R.id.dinnerInsulin);
        if(model.getDinInsulin()!=null){
            dinInsulin.setText(model.getDinInsulin().toString());
        }
        dinInsulin.addTextChangedListener(factory.getDinnerInsulinListener());
    }

    private void setDinCarbs(){
        EditText dinCarbs =(EditText) icrDetails.findViewById(R.id.dinnerCarbs);
        if(model.getDinCarbs()!=null){
            dinCarbs.setText(model.getDinCarbs().toString());
        }
        dinCarbs.addTextChangedListener(factory.getDinnerCarbListener());
    }

    private void setNighInsulin(){
        EditText nighInsulin =(EditText) icrDetails.findViewById(R.id.nightInsulin);
        if(model.getNighInsulin()!=null){
            nighInsulin.setText(model.getNighInsulin().toString());
        }
        nighInsulin.addTextChangedListener(factory.getNightInsulinListener());
    }

    private void setNighCarbs(){
        EditText nighCarbs =(EditText) icrDetails.findViewById(R.id.nightCarbs);
        if(model.getNighCarbs()!=null){
            nighCarbs.setText(model.getNighCarbs().toString());
        }
        nighCarbs.addTextChangedListener(factory.getNightCarbListener());
    }

    private void setISFDetails(){
        insulinSensitivityFactorDetailsHolder.removeAllViews();
        if(model.knowsISF()){
            knewISF=true;
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout holder = new LinearLayout(context);
            LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            holder.setLayoutParams(layoutParams);
            isfDetails = inflater.inflate(R.layout.know_isf,holder,false);
            setMornISF();
            setAfteISF();
            setEveISF();
            setNighISF();
            insulinSensitivityFactorDetailsHolder.addView(isfDetails);
        }else{
            knewISF=false;
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout holder = new LinearLayout(context);
            LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            holder.setLayoutParams(layoutParams);
            isfDetails = inflater.inflate(R.layout.dont_know_isf,holder,false);
            setNumBasBolUnits();
            setRapidActing();
            setISFResult();
            insulinSensitivityFactorDetailsHolder.addView(isfDetails);
        }
    }

    private void setMornISF(){
        EditText mornISF =(EditText) isfDetails.findViewById(R.id.mornISF);
        if(model.getMornISF()!=null){
            mornISF.setText(model.getMornISF().toString());
        }
        mornISF.addTextChangedListener(factory.getMornISFListener());
    }


    private void setAfteISF(){
        EditText afteISF =(EditText) isfDetails.findViewById(R.id.afteISF);
        if(model.getAfteISF()!=null){
            afteISF.setText(model.getAfteISF().toString());
        }
        afteISF.addTextChangedListener(factory.getAfteISFListener());
    }

    private void setEveISF(){
        EditText eveISF =(EditText) isfDetails.findViewById(R.id.eveISF);
        if(model.getEveISF()!=null){
            eveISF.setText(model.getEveISF().toString());
        }
        eveISF.addTextChangedListener(factory.getEveISFListener());
    }

    private void setNighISF(){
        EditText nighISF =(EditText) isfDetails.findViewById(R.id.nighISF);
        if(model.getNighISF()!=null){
            nighISF.setText(model.getNighISF().toString());
        }
        nighISF.addTextChangedListener(factory.getNighISFListener());
    }

    private void setNumBasBolUnits(){
        EditText numBasBol =(EditText) isfDetails.findViewById(R.id.numBasBolUnits);
        if(model.getNumBasBolUnitsPerDay()!=null){
            numBasBol.setText(model.getNumBasBolUnitsPerDay().toString());
        }
        numBasBol.addTextChangedListener(factory.getNumBolusBasalUnitsListener());
    }

    private void setRapidActing(){
        CheckBox rapidActing =(CheckBox) isfDetails.findViewById(R.id.rapidActing);
        rapidActing.setChecked(model.isRapidActing());
        rapidActing.setOnCheckedChangeListener(factory.getRapidActingListener());
    }

    private void setISFResult(){
        TextView isfResult = (TextView) isfDetails.findViewById(R.id.isfResult);
        if(model.getISF()==null){
            isfResult.setText("Enter your details to calculate your approximate insulin sensitivity factor.");
        }else {
            isfResult.setText("This gives an approximate insulin sensitvity factor of: " + model.getISF());
        }
    }

    @Override
    public void update() {
        handleError(model.getError());
        if(knewICR!=model.knowsICR()){
            setICRDetails();
        }
        if(knewISF!=model.knowsISF()){
            setISFDetails();
        }
        if(!model.knowsICR()){
            setICRResult();
        }
        if(!model.knowsISF()){
            setISFResult();
        }
    }
}
