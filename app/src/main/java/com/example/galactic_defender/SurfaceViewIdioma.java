//package com.example.galactic_defender;
//
//import android.content.Context;
//import android.content.res.Resources;
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.Rect;
//import android.os.Build;
//import android.util.DisplayMetrics;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.MotionEvent;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;
//import android.view.inputmethod.InputMethodManager;
//
//import java.util.Locale;
//
//public class SurfaceViewIdioma  extends SurfaceView implements SurfaceHolder.Callback{
//    private SurfaceHolder surfaceHolder;      // Interfaz abstracta para manejar la superficie de dibujado
//    private Context context;                              // Contexto de la aplicación
//    private Bitmap bitmapFondo;                   // Imagen de fondo
//    private int anchoPantalla=1;                      // Ancho de la pantalla, su valor se actualiza en el método surfaceChanged
//    private int altoPantalla=1;                       // Alto de la pantalla, su valor se actualiza en el método surfaceChanged
//    private Hilo hilo;                                        // Hilo encargado de dibujar y actualizar la física
//    private boolean funcionando = false;   // Control del hilo
//    Paint pTexto,pTexto2;
//    String cad;
//    Rect gl,es, en;
//    boolean mostar=false;
//
//    /* Ficheros strings usados:
//    Strings por defecto
//    <resources>
//    <string name="app_name">SurfaceTeclado</string>
//    <string name="cad">Castellano</string>
//    <string name="galego">Gallego</string>
//    <string name="castelan">Castellano</string>
//    <string name="ingles">Inglés</string>
//    </resources>
//
//    Strings galego
//    <resources>
//        <string name="app_name">SurfaceTeclado</string>
//        <string name="cad">Galego</string>
//        <string name="galego">Galego</string>
//        <string name="castelan">Castelán</string>
//        <string name="ingles">Inglés</string>
//    </resources>
//
//    Strings ingles
//    <resources>
//        <string name="app_name">SurfaceTeclado</string>
//        <string name="cad">English</string>
//        <string name="galego">Galician</string>
//        <string name="castelan">Spanish</string>
//        <string name="ingles">English</string>
//    </resources>
//
//     */
//
//    /**
//     * Establece el idioma del sistema
////     * @param codIdioma Codigo del nuevo lenguaje
//     */
////    public void cambiaIdioma(String codIdioma) {
////        Resources res=context.getResources();
////        DisplayMetrics dm=res.getDisplayMetrics();
////        android.content.res.Configuration conf=res.getConfiguration();
////        conf.locale=new Locale(codIdioma.toLowerCase());
////        res.updateConfiguration(conf, dm);
////    }
//
////    public SurfaceViewIdioma(Context context) {   // Constructor
////        super(context);
////
////        this.surfaceHolder = getHolder();                 //  Se obtiene el  holder
////        this.surfaceHolder.addCallback(this);         //  y se indica donde van las funciones callback
////        this.context = context;                                   // Obtenemos el contexto
////
////        hilo = new Hilo();                                            // Inicializamos el hilo
////        cambiaIdioma("gl");
////
////        setFocusable(true);
////
////        // Aseguramos que reciba eventos de toque
////        pTexto=new Paint();
////        pTexto.setColor(Color.WHITE);
////        pTexto.setTextAlign(Paint.Align.CENTER);
////        pTexto2=new Paint();
////        pTexto2.setColor(Color.BLACK);
////        pTexto2.setTextAlign(Paint.Align.CENTER);
////
////    }
//
////    public void actualizarFisica(){           // Actualizamos la física de los elementos en pantalla
////
////    }
//
//
//
////    public void dibujar(Canvas c) {        // Rutina de dibujo en el lienzo. Se le llamará desde el hilo
////        try {
////            c.drawColor(Color.BLUE);
////            c.drawRect(es,pTexto);
//////            c.drawText(context.getText(R.string.castelan).toString(),es.centerX(),es.centerY()+pTexto2.getTextSize()/2.5f,pTexto2);
////            c.drawRect(gl,pTexto);
//////            c.drawText(context.getText(R.string.galego).toString(),gl.centerX(),gl.centerY()+pTexto2.getTextSize()/2.5f,pTexto2);
////            c.drawRect(en,pTexto);
//////            c.drawText(context.getText(R.string.ingles).toString(),en.centerX(),en.centerY()+pTexto2.getTextSize()/2.5f,pTexto2);
//////            c.drawText(context.getText(R.string.cad).toString() , anchoPantalla/2, 200, pTexto);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
//
////    @Override
////    public boolean onTouchEvent(MotionEvent event) {
////        int accion = event.getAction(); //  Solo gestiona la pulsación de un dedo.
////
////        int x = (int)event.getX();
////        int y = (int)event.getY();
////        switch (accion) {
////            case MotionEvent.ACTION_UP:
////                if (gl.contains(x,y)){
////                    cambiaIdioma("gl");
////                }else if (es.contains(x,y)){
////                    cambiaIdioma("es");
////                } if (en.contains(x,y)){
////                cambiaIdioma("en");
////            }
////                return true;
////        }
////        return true;
////    }
////
////    // Callbacks del SurfaceHolder ///////////////////////////////////
////    @Override         // En cuanto se crea el SurfaceView se lanze el hilo
////    public void surfaceCreated(SurfaceHolder holder) {
////        hilo.setFuncionando(true);
////        if (hilo.getState() == Thread.State.NEW) hilo.start();
////        if (hilo.getState() == Thread.State.TERMINATED) {
////            hilo=new Hilo();
////            hilo.start();
////        }
////    }
//
////    // Si hay algún cambio en la superficie de dibujo (normalmente su tamaño) obtenemos el nuevo tamaño
////    @Override
////    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
////        anchoPantalla = width;
////        altoPantalla = height;
////        hilo.setSurfaceSize(width,height);
////        pTexto.setTextSize(anchoPantalla/10);
////        pTexto2.setTextSize(anchoPantalla/25);
////        int tanx=anchoPantalla/10;
////        int tany=altoPantalla/10;
////        es=new Rect(tanx*1,tany*2,tanx*3,tany*3);
////        gl=new Rect(tanx*4,tany*2,tanx*6,tany*3);
////        en=new Rect(tanx*7,tany*2,tanx*9,tany*3);
////    }
//
////    // Al finalizar el surface, se para el hilo
////    @Override
////    public void surfaceDestroyed(SurfaceHolder holder) {
////        hilo.setFuncionando(false);
////        try {
////            hilo.join();
////        } catch (InterruptedException e) {
////            e.printStackTrace();
////        }
////    }
//    // Clase Hilo en la cual se ejecuta el método de dibujo y de física para que se haga en paralelo con la
//    // gestión de la interfaz de usuario
////    class Hilo extends Thread {
////        public Hilo(){
////        }
////
////        @Override
////        public void run() {
////            while (funcionando) {
////                Canvas c = null;    //Siempre es necesario repintar todo el lienzo
////                try {
////                    if (!surfaceHolder.getSurface().isValid())
////                        continue;   // si la superficie no está preparada repetimos
////
////                    //c = surfaceHolder.lockCanvas();                    // Obtenemos el lienzo con aceleración software
////                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
////                        c = surfaceHolder.lockHardwareCanvas();   // Obtenemos el lienzo con Aceleración Hw. Desde la API 26
////                    }
////                    if (c == null) c = surfaceHolder.lockCanvas();
////                    synchronized (surfaceHolder) {   // La sincronización es necesaria por ser recurso común
////                        actualizarFisica();                   // Movimiento de los elementos
////                        dibujar(c);                               // Dibujamos los elementos
////                    }
////                }catch (Exception e){
////                    e.printStackTrace();
////                }  finally {                                               // Haya o no excepción, hay que liberar el lienzo
////                    if (c != null) {
////                        surfaceHolder.unlockCanvasAndPost(c);
////                    }
////                }
////            }
////        }
//
////        // Activa o desactiva el funcionamiento del hilo
////        void setFuncionando(boolean flag) {
////            funcionando = flag;
////        }
////
////        // Función llamada si cambia el tamaño del view
////        public void setSurfaceSize(int width, int height) {
////        }
//    }
//}
