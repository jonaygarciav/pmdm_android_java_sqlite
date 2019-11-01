package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText et_codigo;
    private EditText et_nombre;

    private Button bt_insertar;
    private Button bt_actualizar;
    private Button bt_eliminar;
    private Button bt_consultar;

    private TextView tv_resultado;

    private SQLiteDatabase db;
    private UsuariosSQLiteHelper usdbh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_codigo = findViewById(R.id.et_codigo);
        et_nombre = findViewById(R.id.et_nombre);

        bt_insertar = findViewById(R.id.bt_insertar);
        bt_actualizar = findViewById(R.id.bt_actualizar);
        bt_eliminar = findViewById(R.id.bt_eliminar);
        bt_consultar = findViewById(R.id.bt_consultar);

        tv_resultado = findViewById(R.id.tv_resultado);

        //Abrimos la base de datos 'UsuariosDb' en modo escritura
        usdbh = new UsuariosSQLiteHelper(this, "UsuariosDb", null, 1);

        db = usdbh.getWritableDatabase();
/*
        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {
            //Insertamos 5 usuarios de ejemplo
            for(int i=1; i<=5; i++)
            {
                //Generamos los datos
                int codigo = i;
                String nombre = "Usuario" + i;
                //Insertamos los datos en la tabla Usuarios
                db.execSQL("INSERT INTO Usuarios (codigo, nombre) " +
                        "VALUES (" + codigo + ", '" + nombre +"')");
            }
            //Cerramos la base de datos
            db.close();
        }
*/

        bt_insertar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                db = usdbh.getWritableDatabase();

                //Recuperamos los valores de los campos de texto
                String cod = et_codigo.getText().toString();
                String nom = et_nombre.getText().toString();

                //Alternativa 1: método sqlExec()
                //String sql = "INSERT INTO Usuarios (codigo,nombre) VALUES ('" + cod + "','" + nom + "') ";
                //db.execSQL(sql);

                //Alternativa 2: método insert()
                ContentValues nuevoRegistro = new ContentValues();
                nuevoRegistro.put("codigo", cod);
                nuevoRegistro.put("nombre", nom);
                db.insert("Usuarios", null, nuevoRegistro);

                db.close();
            }
        });

        bt_consultar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                db = usdbh.getWritableDatabase();
                //Alternativa 1: método rawQuery()
                Cursor c = db.rawQuery("SELECT codigo, nombre FROM Usuarios", null);

                //Alternativa 2: método delete()
                //String[] campos = new String[] {"codigo", "nombre"};
                //Cursor c = db.query("Usuarios", campos, null, null, null, null, null);

                //Recorremos los resultados para mostrarlos en pantalla
                tv_resultado.setText("");
                if (c.moveToFirst()) {
                    //Recorremos el cursor hasta que no haya más registros
                    do {
                        String cod = c.getString(0);
                        String nom = c.getString(1);

                        tv_resultado.append(" " + cod + " - " + nom + "\n");
                    } while(c.moveToNext());
                }

                db.close();
            }
        });

        bt_actualizar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                db = usdbh.getWritableDatabase();

                //Recuperamos los valores de los campos de texto
                String cod = et_codigo.getText().toString();
                String nom = et_nombre.getText().toString();

                //Alternativa 1: método sqlExec()
                //String sql = "UPDATE Usuarios SET nombre='" + nom + "' WHERE codigo=" + cod;
                //db.execSQL(sql);

                //Alternativa 2: método update()
                ContentValues valores = new ContentValues();
                valores.put("nombre", nom);
                db.update("Usuarios", valores, "codigo=" + cod, null);

                db.close();
            }
        });

        bt_eliminar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                db = usdbh.getWritableDatabase();

                //Recuperamos los valores de los campos de texto
                String cod = et_codigo.getText().toString();

                //Alternativa 1: método sqlExec()
                //String sql = "DELETE FROM Usuarios WHERE codigo=" + cod;
                //db.execSQL(sql);

                //Alternativa 2: método delete()
                db.delete("Usuarios", "codigo=" + cod, null);

                db.close();
            }
        });

    }

}
