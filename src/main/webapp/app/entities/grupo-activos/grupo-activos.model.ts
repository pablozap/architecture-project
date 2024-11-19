import { IRiesgo } from 'app/entities/riesgo/riesgo.model';
import { Disponibilidad } from 'app/entities/enumerations/disponibilidad.model';
import { Integridad } from 'app/entities/enumerations/integridad.model';
import { Confidencialidad } from 'app/entities/enumerations/confidencialidad.model';
import { Criticidad } from 'app/entities/enumerations/criticidad.model';

export interface IGrupoActivos {
  id: number;
  nombre?: string | null;
  disponibilidad?: keyof typeof Disponibilidad | null;
  integridad?: keyof typeof Integridad | null;
  confidencialidad?: keyof typeof Confidencialidad | null;
  criticidad?: keyof typeof Criticidad | null;
  riesgos?: Pick<IRiesgo, 'id' | 'tipoRiesgo'>[] | null;
}

export type NewGrupoActivos = Omit<IGrupoActivos, 'id'> & { id: null };
