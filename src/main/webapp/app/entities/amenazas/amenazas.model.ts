export interface IAmenazas {
  id: number;
  nombre?: string | null;
}

export type NewAmenazas = Omit<IAmenazas, 'id'> & { id: null };
