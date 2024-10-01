-- таблица адресов
CREATE TABLE addresses.address (
	addressid uuid NOT NULL,
	addressexpand VARCHAR(256) NOT NULL
	CONSTRAINT address_pk PRIMARY KEY (addressid)
);

-- автоматически вычисляемый столбец с адресом в оптимизированном для текстового поиска виде
ALTER TABLE addresses.address
  ADD COLUMN addressvector tsvector GENERATED ALWAYS
   AS (to_tsvector('russian', addressexpand)) STORED;

-- оптимизирует поиск по вектору адреса
CREATE INDEX address_addressexpand_vector
    ON addresses.address
 USING gin (addressvector);

-- оптимизирует вычисление схожести по триграммам
CREATE INDEX address_addressexpand_trgm_ops
    ON addresses.address
 USING gist (addressexpand gist_trgm_ops);
