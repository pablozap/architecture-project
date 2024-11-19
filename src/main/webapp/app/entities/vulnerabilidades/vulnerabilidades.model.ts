export interface IVulnerabilidades {
  id: number;
  nombre?: string | null;
  descripcion?: string | null;
}

export type NewVulnerabilidades = Omit<IVulnerabilidades, 'id'> & { id: null };
