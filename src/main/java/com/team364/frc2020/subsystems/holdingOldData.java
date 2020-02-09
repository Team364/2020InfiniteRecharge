/*

    public void reCalConfigMap() {
        reCalibrate.setLength(0);
        try {
            reCalibrate.append("(");
            configTargets.forEach((hold, value) -> {
                reCalibrate.append("    \"" + hold + "\"" + ",  " + value + ",");
            });
            reCalibrate.append("    \"EasterEgg\"" + ",  " + 69.0 + "    )");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String reCalElementOfSystemMap(double height, double value) {
        String buildHolder = "     " + height + ",  " + value + ",";
        return buildHolder;
    }

    @Override
    public void periodic() {
        boolean calibrateMODE = true;
        boolean calibrateButton = true;
        boolean removeCalButton = false;
        if (THE_SWITCH) {
            findClosestTargets(visionTrack());
            TARGET.put("Shooter", linearInterpolation(closestTarget[0], closestTarget[1], VISIONSPEED, limeY()));
            TARGET.put("Hood", linearInterpolation(closestTarget[0], closestTarget[1], VISIONHOOD, limeY()));
        } else if (calibrateMODE) {
            String name = configName.getString("oof");
            double height = limeY();
            double shooter = configShooter.getDouble(1.0);
            double hood = configHood.getDouble(1.0);
            double lastHeight = configLastHeight.getDouble(0.0);
            if (calibrateButton && height != lastHeight) {//
                configTargets.put(name, height);
                reCalShootMap.append(reCalElementOfSystemMap(height, shooter));
                reCalHoodMap.append(reCalElementOfSystemMap(height, hood));
                reCalConfigMap();
                ConfigMap.setString(reCalibrate.toString());
                ShooterMap.setString(reCalShootMap.toString());
                HoodMap.setString(reCalHoodMap.toString());
                SmartDashboard.putString("wow", reCalibrate.toString());

                //lastHeight = height;
            } else if (lastHeight == -1.0) {
                reCalShootMap.setLength(0);
                reCalHoodMap.setLength(0);
                ConfigMap.setString(reCalibrate.toString());

            }
            SmartDashboard.putNumber("info", height);
            SmartDashboard.putNumber("other info", lastHeight);

       }  
       cycles++;
   }
   */