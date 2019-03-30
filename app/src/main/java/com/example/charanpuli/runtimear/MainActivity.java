package com.example.charanpuli.runtimear;

import android.database.CursorIndexOutOfBoundsException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.ux.ArFragment;

public class MainActivity extends AppCompatActivity {
    private ArFragment arFragment;
    public enum ShapeType{
        SPHERE,
        CUBE,
        CYLINDER
    }
    private Button sphere,cube,cylinder;
    public ShapeType shapeType=ShapeType.CUBE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arFragment=(ArFragment)getSupportFragmentManager().findFragmentById(R.id.arFragment);

        sphere=(Button)findViewById(R.id.sphere);
        cube=(Button)findViewById(R.id.cube);
        cylinder=(Button)findViewById(R.id.cylinder);


        sphere.setOnClickListener(view->shapeType=ShapeType.SPHERE);
        cube.setOnClickListener(view->shapeType=ShapeType.CUBE);
        cylinder.setOnClickListener(view->shapeType=ShapeType.CYLINDER);

        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            if(shapeType==ShapeType.CUBE){
                placeCube(hitResult.createAnchor());
            }
            else if(shapeType==ShapeType.SPHERE){
                placeSphere(hitResult.createAnchor());
            }
            else{
                placeCylinder(hitResult.createAnchor());
            }
        });



    }

    private void placeCube(Anchor anchor) {
        MaterialFactory.makeOpaqueWithColor(this,new Color(android.graphics.Color.BLACK))
                .thenAccept(material -> {
                    ModelRenderable renderable=ShapeFactory.makeCube(new Vector3(0.1f,0.1f,0.1f),new Vector3(0f,0.1f,0f),material);
                    buildModel(renderable,anchor);
                });

    }
    private void placeSphere(Anchor anchor) {
        MaterialFactory.makeOpaqueWithColor(this,new Color(android.graphics.Color.BLACK))
                .thenAccept(material -> {
                    ModelRenderable renderable=ShapeFactory.makeSphere(0.1f,new Vector3(0f,0.1f,0f),material);
                    buildModel(renderable,anchor);
                });

    }
    private void placeCylinder(Anchor anchor) {
        MaterialFactory.makeOpaqueWithColor(this,new Color(android.graphics.Color.BLACK))
                .thenAccept(material -> {
                    ModelRenderable renderable=ShapeFactory.makeCylinder(0.1f,0.2f,new Vector3(0f,0.2f,0f),material);
                    buildModel(renderable,anchor);
                });

    }

    private void buildModel(ModelRenderable renderable, Anchor anchor) {
        AnchorNode anchorNode=new AnchorNode(anchor);
        anchorNode.setRenderable(renderable);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
    }
}
