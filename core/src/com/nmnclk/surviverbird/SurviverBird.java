package com.nmnclk.surviverbird;



import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;


import java.util.Random;

public class SurviverBird extends ApplicationAdapter {

	SpriteBatch batch;
	Texture arkaplan;
	Texture arkaplan2;
	Texture kus;
	Texture ari1;
	Texture ari2;
	Texture ari3;

	int enYuksekScore=0;

	int score=0;
	int scoreEnemy=0;//duşman indis değeri


	Random random;

	float kusX=0;
	float kusY=0;
	int gameState=0;
	float velocity=0;
	float gravity=1;
	float arkaplanX=0;
	float arkaplan2X=0;
	float boyutGenislik;
	float boyutYukseklik;
	float dusmanHizi=7;


	float arkaplanHizi=2;

	int dusmanSayisi=2;
	float [] dusmanX=new float[dusmanSayisi];
	float aralık=0;

	float[] enemyOffSet1=new float[dusmanSayisi];
	float[] enemyOffSet2=new float[dusmanSayisi];
	float[] enemyOffSet3=new float[dusmanSayisi];


	Circle birdCircle;
	Circle [] enemyCircle1;
	Circle [] enemyCircle2;
	Circle [] enemyCircle3;

	ShapeRenderer shapeRenderer;


	BitmapFont font;
	BitmapFont fontson;



	@Override
	public void create () {
		batch = new SpriteBatch();
		arkaplan=new Texture("arkaplan.png");
		arkaplan2=new Texture("arkaplan.png");
		kus=new Texture("kus.png");
		ari1=new Texture("ari.png");
		ari2=new Texture("ari.png");
		ari3=new Texture("ari.png");



		aralık=Gdx.graphics.getWidth()/2;

				random=new Random();
		scoreEnemy=0;
		score=0;

		font=new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);

		fontson=new BitmapFont();
		fontson.setColor(Color.WHITE);
		fontson.getData().setScale(5);

		kusX=Gdx.graphics.getWidth()/4;
		kusY=Gdx.graphics.getHeight()/2;

		birdCircle=new Circle();
		enemyCircle1=new Circle[dusmanSayisi];
		enemyCircle2=new Circle[dusmanSayisi];
		enemyCircle3=new Circle[dusmanSayisi];

		shapeRenderer=new ShapeRenderer();

		boyutGenislik=Gdx.graphics.getWidth()/15;
		boyutYukseklik=Gdx.graphics.getHeight()/10;

		dusmanX[0]=Gdx.graphics.getWidth();
		for(int i=0;i<dusmanSayisi;i++){

			enemyOffSet1[i]=random.nextFloat()*Gdx.graphics.getHeight();
			enemyOffSet2[i]=random.nextFloat()*Gdx.graphics.getHeight();
			enemyOffSet3[i]=random.nextFloat()*Gdx.graphics.getHeight();

			enemyCircle1[i]=new Circle();
			enemyCircle2[i]=new Circle();
			enemyCircle3[i]=new Circle();

			if(i!=0){
				dusmanX[i]=dusmanX[i-1]+Gdx.graphics.getWidth()/2;
			}




		}




	}

	@Override
	public void render () {//render tek çalışması içinde çizilen aynı anda görünür, render tekrar çağırıldığında önce çizilen herşey silinir
		batch.begin();

		batch.draw(arkaplan,arkaplanX,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		batch.draw(arkaplan2,arkaplan2X+Gdx.graphics.getWidth(),0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		batch.draw(kus,kusX,kusY,boyutGenislik,boyutYukseklik);




		if (gameState==1){


			if(dusmanX[scoreEnemy]<kusX){
				score++;
				dusmanHizi+=0.2;
				if (scoreEnemy<dusmanSayisi-1)
				{
					scoreEnemy++;
				}
				else {
					scoreEnemy=0;
				}
			}


			if (Gdx.input.justTouched()){
				velocity = -15;

			}

			for(int i=0;i<dusmanSayisi;i++) {
				if(dusmanX[i]<-boyutGenislik){

					if (i==0){
						dusmanX[i]=Gdx.graphics.getWidth();
					}
					else {
						dusmanX[i]=dusmanX[i-1]+Gdx.graphics.getWidth()/2;
					}


					enemyOffSet1[i]=random.nextFloat()*Gdx.graphics.getHeight();
					enemyOffSet2[i]=random.nextFloat()*Gdx.graphics.getHeight();
					enemyOffSet3[i]=random.nextFloat()*Gdx.graphics.getHeight();

				}

				dusmanX[i] -= dusmanHizi;


				batch.draw(ari1, dusmanX[i], enemyOffSet1[i], boyutGenislik, boyutYukseklik);
				batch.draw(ari2, dusmanX[i], enemyOffSet2[i], boyutGenislik, boyutYukseklik);
				batch.draw(ari3, dusmanX[i], enemyOffSet3[i], boyutGenislik, boyutYukseklik);

				enemyCircle1[i]=new Circle( dusmanX[i]+boyutGenislik/2,enemyOffSet1[i]+boyutYukseklik/2, boyutGenislik/2);
				enemyCircle2[i]=new Circle( dusmanX[i]+boyutGenislik/2,enemyOffSet1[i]+boyutYukseklik/2, boyutGenislik/2);
				enemyCircle3[i]=new Circle( dusmanX[i]+boyutGenislik/2,enemyOffSet1[i]+boyutYukseklik/2, boyutGenislik/2);

			}



			if (kusY>0){

				velocity=velocity+gravity;
				kusY-=velocity;
				arkaplanX-=arkaplanHizi;
				arkaplan2X-=arkaplanHizi;
				if (arkaplan2X<-Gdx.graphics.getWidth()){
					arkaplan2X+=Gdx.graphics.getWidth();
				}
				if (arkaplanX<-Gdx.graphics.getWidth()){
					arkaplanX+=Gdx.graphics.getWidth();
				}
			}else {
				gameState=2;
			}

			if(kusY > Gdx.graphics.getHeight()){
				gameState=2;
			}



		}else if(gameState==0) {
			if (Gdx.input.justTouched()){
				gameState=1;

			}


		}
		else if(gameState==2) {
			kusX=Gdx.graphics.getWidth()/4;
			kusY=Gdx.graphics.getHeight()/2;



			for(int i=0;i<dusmanSayisi;i++){


				enemyOffSet1[i]=random.nextInt()%Gdx.graphics.getHeight();
				enemyOffSet2[i]=random.nextInt()%Gdx.graphics.getHeight();
				enemyOffSet3[i]=random.nextInt()%Gdx.graphics.getHeight();

				dusmanX[i]=Gdx.graphics.getWidth()- ari1.getHeight()/2+i*aralık;

				enemyCircle1[i]=new Circle();
				enemyCircle2[i]=new Circle();
				enemyCircle3[i]=new Circle();

			}
			velocity=0;

			fontson.draw(batch,"Scorunuz: " + String.valueOf(score) + " tekrar oynamak için tıklayın",Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/2);
			if (Gdx.input.justTouched()){
				gameState=1;
				score=0;
				dusmanHizi=7;

			}
		}

		font.draw(batch,String.valueOf(score),100,200);



		batch.end();
		birdCircle.set(kusX+boyutGenislik/2,kusY+boyutYukseklik/2,boyutGenislik/2);
		//shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		//shapeRenderer.setColor(Color.BLACK);
		//shapeRenderer.circle(birdCircle.x,birdCircle.y,birdCircle.radius);


		for(int i=0;i<dusmanSayisi;i++){
			//shapeRenderer.circle(dusmanX[i]+boyutGenislik/2,enemyOffSet1[i]+boyutYukseklik/2, boyutGenislik/2);
			//shapeRenderer.circle(dusmanX[i]+boyutGenislik/2,enemyOffSet2[i]+boyutYukseklik/2, boyutGenislik/2);
			//shapeRenderer.circle(dusmanX[i]+boyutGenislik/2,enemyOffSet3[i]+boyutYukseklik/2, boyutGenislik/2);
			if(Intersector.overlaps( birdCircle,enemyCircle1[i]) || Intersector.overlaps( birdCircle,enemyCircle2[i]) || Intersector.overlaps( birdCircle,enemyCircle3[i])  	)
			{
				gameState=2;


			}
		}
		//shapeRenderer.end();
	}

	@Override
	public void dispose () {

	}
}
