SELECT * FROM ngramchecker.trigram_pos_frequency as a INNER JOIN ngramchecker.trigram as b ON a.id = b.posID WHERE pos LIKE 'VBTR PRS CCB'