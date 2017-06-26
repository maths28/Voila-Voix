package com.mycompany.myapp.service.hardCodedVlues;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by user on 23/06/2017.
 */
@CrossOrigin(origins = "*")
@RestController
public class HardCodedValues {

    @RequestMapping("/demojson")
    public String demoJson() {
        String demo = "{\n" +
            "                    \"job\": {\n" +
            "                        \"lang\": \"fr\",\n" +
            "                        \"user_id\": 12066,\n" +
            "                        \"name\": \"camus1.mp3\",\n" +
            "                        \"duration\": 60,\n" +
            "                        \"created_at\": \"Thu Mar  2 20:29:50 2017\",\n" +
            "                        \"id\": 2071638\n" +
            "                    },\n" +
            "                    \"speakers\": [\n" +
            "                        {\n" +
            "                            \"duration\": \"4.350000\",\n" +
            "                            \"confidence\": null,\n" +
            "                            \"name\": \"M1\",\n" +
            "                            \"time\": \"5.480000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"49.950000\",\n" +
            "                            \"confidence\": null,\n" +
            "                            \"name\": \"M2\",\n" +
            "                            \"time\": \"9.830000\"\n" +
            "                        }\n" +
            "                    ],\n" +
            "                    \"words\": [\n" +
            "                        {\n" +
            "                            \"duration\": \"0.480000\",\n" +
            "                            \"confidence\": \"0.800\",\n" +
            "                            \"name\": \"Madame\",\n" +
            "                            \"time\": \"5.480000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.510000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"Altesse\",\n" +
            "                            \"time\": \"6.020000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.510000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"Royale\",\n" +
            "                            \"time\": \"6.530000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.510000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"mesdames\",\n" +
            "                            \"time\": \"7.130000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.540000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"messieurs\",\n" +
            "                            \"time\": \"7.730000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"1.560000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \".\",\n" +
            "                            \"time\": \"8.270000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"1.560000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"En\",\n" +
            "                            \"time\": \"9.830000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.660000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"recevant\",\n" +
            "                            \"time\": \"9.950000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.150000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"la\",\n" +
            "                            \"time\": \"10.610000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.900000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"distinction\",\n" +
            "                            \"time\": \"10.760000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.240000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"dans\",\n" +
            "                            \"time\": \"11.660000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.410000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"votre\",\n" +
            "                            \"time\": \"11.900000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.450000\",\n" +
            "                            \"confidence\": \"0.610\",\n" +
            "                            \"name\": \"Libre\",\n" +
            "                            \"time\": \"12.320000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.540000\",\n" +
            "                            \"confidence\": \"0.690\",\n" +
            "                            \"name\": \"Académie\",\n" +
            "                            \"time\": \"12.770000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.090000\",\n" +
            "                            \"confidence\": \"0.990\",\n" +
            "                            \"name\": \"a\",\n" +
            "                            \"time\": \"13.310000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.240000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"bien\",\n" +
            "                            \"time\": \"13.400000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.270000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"voulu\",\n" +
            "                            \"time\": \"13.640000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.420000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"honorer\",\n" +
            "                            \"time\": \"13.970000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"1.260000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \".\",\n" +
            "                            \"time\": \"14.390000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"1.260000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"Ma\",\n" +
            "                            \"time\": \"15.650000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.630000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"gratitude\",\n" +
            "                            \"time\": \"15.770000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.180000\",\n" +
            "                            \"confidence\": \"0.990\",\n" +
            "                            \"name\": \"était\",\n" +
            "                            \"time\": \"16.400000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.390000\",\n" +
            "                            \"confidence\": \"0.960\",\n" +
            "                            \"name\": \"d'autant\",\n" +
            "                            \"time\": \"16.580000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.210000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"plus\",\n" +
            "                            \"time\": \"16.970000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.780000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"profonde\",\n" +
            "                            \"time\": \"17.180000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.090000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"que\",\n" +
            "                            \"time\": \"19.010000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.180000\",\n" +
            "                            \"confidence\": \"0.990\",\n" +
            "                            \"name\": \"j'ai\",\n" +
            "                            \"time\": \"19.100000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.540000\",\n" +
            "                            \"confidence\": \"0.990\",\n" +
            "                            \"name\": \"mesuré\",\n" +
            "                            \"time\": \"19.280000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.060000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"à\",\n" +
            "                            \"time\": \"19.820000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.240000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"quel\",\n" +
            "                            \"time\": \"19.880000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.300000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"point\",\n" +
            "                            \"time\": \"20.120000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.270000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"cette\",\n" +
            "                            \"time\": \"20.420000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.720000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"récompense\",\n" +
            "                            \"time\": \"20.690000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.570000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"dépassait\",\n" +
            "                            \"time\": \"21.440000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.150000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"mes\",\n" +
            "                            \"time\": \"22.010000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.480000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"mérites\",\n" +
            "                            \"time\": \"22.160000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.690000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"personnels\",\n" +
            "                            \"time\": \"22.670000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"1.290000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \".\",\n" +
            "                            \"time\": \"23.360000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"1.290000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"Tout\",\n" +
            "                            \"time\": \"24.650000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.380000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"homme\",\n" +
            "                            \"time\": \"24.920000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.220000\",\n" +
            "                            \"confidence\": \"0.920\",\n" +
            "                            \"name\": \"et\",\n" +
            "                            \"time\": \"25.370000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.120000\",\n" +
            "                            \"confidence\": \"0.990\",\n" +
            "                            \"name\": \"à\",\n" +
            "                            \"time\": \"25.640000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.180000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"plus\",\n" +
            "                            \"time\": \"25.760000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.390000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"forte\",\n" +
            "                            \"time\": \"25.940000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.440000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"raison\",\n" +
            "                            \"time\": \"26.330000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.240000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"tout\",\n" +
            "                            \"time\": \"26.780000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.630000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"artiste\",\n" +
            "                            \"time\": \"27.020000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.690000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \".\",\n" +
            "                            \"time\": \"27.650000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.690000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"Désire\",\n" +
            "                            \"time\": \"28.340000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.240000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"être\",\n" +
            "                            \"time\": \"28.970000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.540000\",\n" +
            "                            \"confidence\": \"0.870\",\n" +
            "                            \"name\": \"reconnu\",\n" +
            "                            \"time\": \"29.210000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.500000\",\n" +
            "                            \"confidence\": \"0.890\",\n" +
            "                            \"name\": \".\",\n" +
            "                            \"time\": \"29.780000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.150000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"Je\",\n" +
            "                            \"time\": \"30.980000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.120000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"le\",\n" +
            "                            \"time\": \"31.130000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.480000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"désire\",\n" +
            "                            \"time\": \"31.250000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.280000\",\n" +
            "                            \"confidence\": \"0.730\",\n" +
            "                            \"name\": \"aussi\",\n" +
            "                            \"time\": \"31.750000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"1.380000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \".\",\n" +
            "                            \"time\": \"32.030000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"1.380000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"Mais\",\n" +
            "                            \"time\": \"33.410000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.120000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"il\",\n" +
            "                            \"time\": \"33.560000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.120000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"ne\",\n" +
            "                            \"time\": \"33.680000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.120000\",\n" +
            "                            \"confidence\": \"0.940\",\n" +
            "                            \"name\": \"m'a\",\n" +
            "                            \"time\": \"33.800000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.240000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"pas\",\n" +
            "                            \"time\": \"33.920000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.240000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"été\",\n" +
            "                            \"time\": \"34.160000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.750000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"possible\",\n" +
            "                            \"time\": \"34.400000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.720000\",\n" +
            "                            \"confidence\": \"0.990\",\n" +
            "                            \"name\": \"d'apprendre\",\n" +
            "                            \"time\": \"35.270000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.270000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"votre\",\n" +
            "                            \"time\": \"35.990000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.930000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"décision\",\n" +
            "                            \"time\": \"36.260000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.930000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \".\",\n" +
            "                            \"time\": \"37.190000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.930000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"Sans\",\n" +
            "                            \"time\": \"38.120000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.810000\",\n" +
            "                            \"confidence\": \"0.870\",\n" +
            "                            \"name\": \"comparaison\",\n" +
            "                            \"time\": \"38.420000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"1.080000\",\n" +
            "                            \"confidence\": \"0.750\",\n" +
            "                            \"name\": \"retentissements\",\n" +
            "                            \"time\": \"39.230000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.120000\",\n" +
            "                            \"confidence\": \"0.980\",\n" +
            "                            \"name\": \"à\",\n" +
            "                            \"time\": \"40.400000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.240000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"ce\",\n" +
            "                            \"time\": \"40.520000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.120000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"que\",\n" +
            "                            \"time\": \"40.760000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.180000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"je\",\n" +
            "                            \"time\": \"40.880000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.360000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"suis\",\n" +
            "                            \"time\": \"41.060000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.550000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"réellement\",\n" +
            "                            \"time\": \"41.480000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.520000\",\n" +
            "                            \"confidence\": \"0.600\",\n" +
            "                            \"name\": \".\",\n" +
            "                            \"time\": \"42.040000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.360000\",\n" +
            "                            \"confidence\": \"0.560\",\n" +
            "                            \"name\": \"Comment\",\n" +
            "                            \"time\": \"43.640000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.270000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"un\",\n" +
            "                            \"time\": \"44.000000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.480000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"homme\",\n" +
            "                            \"time\": \"44.270000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.510000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"presque\",\n" +
            "                            \"time\": \"44.840000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.480000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"jeune\",\n" +
            "                            \"time\": \"45.350000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.540000\",\n" +
            "                            \"confidence\": \"0.990\",\n" +
            "                            \"name\": \"riche\",\n" +
            "                            \"time\": \"46.750000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.150000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"de\",\n" +
            "                            \"time\": \"47.300000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.210000\",\n" +
            "                            \"confidence\": \"0.880\",\n" +
            "                            \"name\": \"ses\",\n" +
            "                            \"time\": \"47.450000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.350000\",\n" +
            "                            \"confidence\": \"0.950\",\n" +
            "                            \"name\": \"seuls\",\n" +
            "                            \"time\": \"47.660000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.570000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"doutes\",\n" +
            "                            \"time\": \"48.020000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.090000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"et\",\n" +
            "                            \"time\": \"48.710000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.300000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"d'une\",\n" +
            "                            \"time\": \"48.800000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.660000\",\n" +
            "                            \"confidence\": \"0.780\",\n" +
            "                            \"name\": \"oeuvre\",\n" +
            "                            \"time\": \"49.100000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.510000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"encore\",\n" +
            "                            \"time\": \"49.850000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.090000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"en\",\n" +
            "                            \"time\": \"50.360000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.630000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"chantier\",\n" +
            "                            \"time\": \"50.450000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.490000\",\n" +
            "                            \"confidence\": \"0.860\",\n" +
            "                            \"name\": \".\",\n" +
            "                            \"time\": \"51.100000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.630000\",\n" +
            "                            \"confidence\": \"0.730\",\n" +
            "                            \"name\": \"Habitué\",\n" +
            "                            \"time\": \"52.130000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.120000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"à\",\n" +
            "                            \"time\": \"52.760000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.480000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"vivre\",\n" +
            "                            \"time\": \"52.880000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.120000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"dans\",\n" +
            "                            \"time\": \"53.360000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.090000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"la\",\n" +
            "                            \"time\": \"53.480000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.600000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"solitude\",\n" +
            "                            \"time\": \"53.570000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.180000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"du\",\n" +
            "                            \"time\": \"54.170000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.780000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"travail\",\n" +
            "                            \"time\": \"54.350000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.130000\",\n" +
            "                            \"confidence\": \"0.990\",\n" +
            "                            \"name\": \"ou\",\n" +
            "                            \"time\": \"55.930000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.130000\",\n" +
            "                            \"confidence\": \"0.740\",\n" +
            "                            \"name\": \"dans\",\n" +
            "                            \"time\": \"56.060000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.120000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"les\",\n" +
            "                            \"time\": \"56.240000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.690000\",\n" +
            "                            \"confidence\": \"0.990\",\n" +
            "                            \"name\": \"retraites\",\n" +
            "                            \"time\": \"56.360000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.150000\",\n" +
            "                            \"confidence\": \"0.900\",\n" +
            "                            \"name\": \"de\",\n" +
            "                            \"time\": \"57.080000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.690000\",\n" +
            "                            \"confidence\": \"0.980\",\n" +
            "                            \"name\": \"l'amitié\",\n" +
            "                            \"time\": \"57.230000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.300000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"n'aurait\",\n" +
            "                            \"time\": \"58.940000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.180000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"il\",\n" +
            "                            \"time\": \"59.240000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.290000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \"pas\",\n" +
            "                            \"time\": \"59.420000\"\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"duration\": \"0.110000\",\n" +
            "                            \"confidence\": \"1.000\",\n" +
            "                            \"name\": \".\",\n" +
            "                            \"time\": \"59.780000\"\n" +
            "                        }\n" +
            "                    ],\n" +
            "                    \"format\": \"1.0\"\n" +
            "                }";

        return demo;

    }

    @RequestMapping("/timelinesens")
    public String timeLineSens() {

        String sens = "[{\"lane\": 0, \"id\": \"Gastronomie\", \"start\": 3, \"end\": 10}, {\"lane\": 0, \"id\": \"Genin\", \"start\": 11, \"end\": 29}, {\"lane\": 0, \"id\": \"Paris\", \"start\": 30, \"end\": 45}, {\"lane\": 0, \"id\": \"Majestic12\", \"start\": 45, \"end\": 60}]";

        return sens;
    }

}
