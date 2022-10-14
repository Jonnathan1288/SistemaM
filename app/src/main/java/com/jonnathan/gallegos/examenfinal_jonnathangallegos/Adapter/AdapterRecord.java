package com.jonnathan.gallegos.examenfinal_jonnathangallegos.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jonnathan.gallegos.examenfinal_jonnathangallegos.Modelo.ModeloUsuario;
import com.jonnathan.gallegos.examenfinal_jonnathangallegos.Modelo.Usuario;
import com.jonnathan.gallegos.examenfinal_jonnathangallegos.R;
import com.jonnathan.gallegos.examenfinal_jonnathangallegos.detailUser;

import java.util.ArrayList;

public class AdapterRecord extends RecyclerView.Adapter<AdapterRecord.HolderRecord>{

    //Variables
    private Context context;
    private ArrayList<Usuario> recordsList;
    //Constructor
    public AdapterRecord(Context context, ArrayList<Usuario> recordsList){
        this.context = context;
        this.recordsList = recordsList;
    }

    @NonNull
    @Override
    public HolderRecord onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout
        View view = LayoutInflater.from(context).inflate(R.layout.row_record, parent, false);

        return new HolderRecord(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderRecord holder, int position) {

        Usuario model = recordsList.get(position);
        final int id = model.getId_usuario();
        String cedula = model.getCedula();
        String nombre = model.getNombre();
        String contrasenia = model.getContrasenia();
        String permisos = model.getPermisos();
        String image = model.getFoto();

        holder.cedula.setText(cedula);
        holder.nombre.setText(nombre);
        holder.contra.setText(contrasenia);
        holder.permiso.setText(permisos);
        if (image.equals("null")){
            // no hay imagen en el registro, establecer predeterminado
            holder.profileIv.setImageResource(R.drawable.ic_person_black);
        }else {
            holder.profileIv.setImageURI(Uri.parse(image));
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Pass record id to next activity to show details of thet record

                Intent intent = new Intent(context, detailUser.class);
                intent.putExtra("CEDULA", cedula);
                intent.putExtra("NOMBRE", nombre);
                intent.putExtra("CONTRA", contrasenia);
                intent.putExtra("PERMISO", permisos);
                intent.putExtra("FOTO", image);
                context.startActivity(intent);
            }
        });

        holder.moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FALTA
            }
        });
    }

    @Override
    public int getItemCount() {
        return recordsList.size();// devuelve el tamaño de la lista / número o registros
    }

    class HolderRecord extends RecyclerView.ViewHolder{
        //vistas
        ImageView profileIv;
        TextView cedula, nombre, contra, permiso;
        ImageButton moreBtn;
        public HolderRecord(@NonNull View itemView){
            super(itemView);

            //Inicializamos la vistas
            profileIv = itemView.findViewById(R.id.profileIv);
            cedula = itemView.findViewById(R.id.cedulaView);
            nombre = itemView.findViewById(R.id.nombreView);
            contra = itemView.findViewById(R.id.contraView);
            permiso = itemView.findViewById(R.id.permisoView);
            moreBtn = itemView.findViewById(R.id.moreBtn);

        }
    }
}
