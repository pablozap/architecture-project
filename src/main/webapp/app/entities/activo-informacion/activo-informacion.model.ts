import dayjs from 'dayjs/esm';
import { IGrupoActivos } from 'app/entities/grupo-activos/grupo-activos.model';
import { Proceso } from 'app/entities/enumerations/proceso.model';
import { TipoActivo } from 'app/entities/enumerations/tipo-activo.model';
import { ClasificacionInformacion1712 } from 'app/entities/enumerations/clasificacion-informacion-1712.model';
import { Formato } from 'app/entities/enumerations/formato.model';

export interface IActivoInformacion {
  id: number;
  proceso?: keyof typeof Proceso | null;
  nombre?: string | null;
  descripcion?: string | null;
  tipoActivo?: keyof typeof TipoActivo | null;
  ley1581?: boolean | null;
  clasificacionInformacion1712?: keyof typeof ClasificacionInformacion1712 | null;
  ley1266?: boolean | null;
  formato?: keyof typeof Formato | null;
  propietario?: string | null;
  usuario?: string | null;
  custodio?: string | null;
  usuarioFinal?: string | null;
  fecha?: dayjs.Dayjs | null;
  estadoActivo?: string | null;
  fechaIngreso?: dayjs.Dayjs | null;
  fechaRetiro?: dayjs.Dayjs | null;
  grupoActivo?: Pick<IGrupoActivos, 'id' | 'nombre'> | null;
}

export type NewActivoInformacion = Omit<IActivoInformacion, 'id'> & { id: null };
