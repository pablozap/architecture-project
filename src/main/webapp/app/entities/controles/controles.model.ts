import { IRiesgo } from 'app/entities/riesgo/riesgo.model';

export interface IControles {
  id: number;
  nombre?: string | null;
  descripcion?: string | null;
  riesgos?: Pick<IRiesgo, 'id'>[] | null;
}

export type NewControles = Omit<IControles, 'id'> & { id: null };
