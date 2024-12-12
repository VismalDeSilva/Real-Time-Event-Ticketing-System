import React, { useEffect, useState } from 'react'
import styles from './style.module.scss'
import { Helmet } from 'react-helmet'
import Navbar from '../../Components/Navbar'
import axios from 'axios'

const Configurator = () => {
    const [config, setConfig] = useState(null);

    const fetchConfigData = async () => {
        const apiUrl = `http://localhost:8080/api/configure`;
        const response = await axios.get(apiUrl);
        setConfig(response.data);
    };

    const saveConfigData = async () => {
        const apiUrl = `http://localhost:8080/api/configure/setup`;
        try {
          await axios.post(apiUrl, config); // Send the updated config as the request body
          console.log('Configuration saved successfully:', config);
        } catch (error) {
          console.error('Error saving config data:', error);
        }
      };

    useEffect(() => {
        fetchConfigData();
    },[])


    return (
        <div className={styles.container}>
            <Helmet><title>Configurator</title></Helmet>
            <Navbar />
            <div className={styles.configuratorCont}>
                <h2>Configurator</h2>
                <div className={styles.inputContainer}>
                    <label>Total Tickets:</label>
                    <input type="text" placeholder={config?.totalTickets} onChange={(e) => config.totalTickets = Number(e.target.value)}/>
                </div>
                <div className={styles.inputContainer}>
                    <label>Ticket Release Rate:</label>
                    <input type="text" placeholder={config?.ticketReleaseRate} onChange={(e) => config.ticketReleaseRate = Number(e.target.value)}/>
                </div>
                <div className={styles.inputContainer}>
                    <label>Customer Retrieval Rate:</label>
                    <input type="text" placeholder={config?.customerRetrievalRate} onChange={(e) => config.customerRetrievalRate = Number(e.target.value)}/>
                </div>
                <div className={styles.inputContainer}>
                    <label>Max Ticket Capacity:</label>
                    <input type="text" placeholder={config?.maxTicketCapacity} onChange={(e) => config.maxTicketCapacity = Number(e.target.value)}/>
                </div>
                <button onClick={saveConfigData}>Save</button>
            </div>
        </div>
    )
}

export default Configurator